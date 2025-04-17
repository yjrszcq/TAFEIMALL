package cn.edu.xidian.tafei_mall.service.impl;

import cn.edu.xidian.tafei_mall.mapper.ProductMapper;
import cn.edu.xidian.tafei_mall.mapper.PromotionMapper;
import cn.edu.xidian.tafei_mall.mapper.PromotionProductMapper;
import cn.edu.xidian.tafei_mall.model.entity.Promotion;
import cn.edu.xidian.tafei_mall.model.entity.PromotionProduct;
import cn.edu.xidian.tafei_mall.model.vo.PromotionCreateVO;
import cn.edu.xidian.tafei_mall.model.vo.Response.Promotion.PromotionListResponse;
import cn.edu.xidian.tafei_mall.model.vo.Response.Promotion.createPromotionResponse;
import cn.edu.xidian.tafei_mall.model.vo.Response.Promotion.getPromotionResponse;
import cn.edu.xidian.tafei_mall.service.ProductService;
import cn.edu.xidian.tafei_mall.service.PromotionProductService;
import cn.edu.xidian.tafei_mall.service.PromotionService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.edu.xidian.tafei_mall.model.entity.Product;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;


@Slf4j
@Service
public class PromotionServiceImpl extends ServiceImpl<PromotionMapper, Promotion> implements PromotionService {

    @Autowired
    private PromotionMapper promotionMapper;

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private PromotionProductService promotionProductService;
    @Autowired
    private PromotionProductMapper promotionProductMapper;

    @Override
    public createPromotionResponse createPromotion(PromotionCreateVO promotionCreateVO) {

        final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH");

        if (promotionCreateVO == null) {
            return new createPromotionResponse("促销信息不能为空");
        }

        List<String> productIds = promotionCreateVO.getProductIds();
        if (CollectionUtils.isEmpty(productIds)) {
            return new createPromotionResponse("必须至少选择一个商品");
        }

        List<Product> existingProducts = productService.lambdaQuery()
                .in(Product::getProductId, productIds)
                .list();
        /*boolean exists = productService.lambdaQuery()
                .eq(Product::getProductId, promotionCreateVO.getProductIds())
                .exists();
        if (!exists){
            return  new createPromotionResponse("商品下架或不存在");
        }*/
        if (existingProducts.size() != productIds.size()) {
            List<String> existingIds = existingProducts.stream()
                    .map(Product::getProductId)
                    .collect(Collectors.toList());

            List<String> notFoundIds = productIds.stream()
                    .filter(id -> !existingIds.contains(id))
                    .collect(Collectors.toList());

            return new createPromotionResponse("以下商品不存在或已下架: " + String.join(",", notFoundIds));
        }


        BigDecimal discountRate = promotionCreateVO.getDiscountRate();
        if (discountRate == null) {
            return new createPromotionResponse("折扣率不能为空");
        }
        if (discountRate.compareTo(BigDecimal.ZERO) <= 0 ||
                discountRate.compareTo(new BigDecimal("100")) > 0) {
            return new createPromotionResponse("折扣率必须大于0且小于等于100");
        }
        if (discountRate.scale() > 2) {
            return new createPromotionResponse("折扣率最多支持2位小数");
        }

        try {
            LocalDateTime startDate = LocalDateTime.parse(promotionCreateVO.getStartDate(), DATE_TIME_FORMATTER);
            LocalDateTime endDate = LocalDateTime.parse(promotionCreateVO.getEndDate(), DATE_TIME_FORMATTER);


            if (endDate.isBefore(startDate)) {
                return new createPromotionResponse("结束时间不能早于开始时间");
            }

            if (startDate.isBefore(LocalDateTime.now())) {
                return new createPromotionResponse("开始时间不能早于当前时间");
            }

            String promotionId = UUID.randomUUID().toString();

            Promotion promotion = new Promotion();
            promotion.setPromotionId(promotionId);
            promotion.setStartDate(startDate);
            promotion.setEndDate(endDate);
            promotion.setIsActive(true);

            /*List<Promotion> promotions = productIds.stream().map(productId -> {
                Promotion promotion = new Promotion();
                promotion.setPromotionId(UUID.randomUUID().toString());
                promotion.setProductId(productId);
                promotion.setDiscountRate(Double.toString(discountRate));
                promotion.setStartDate(startDate);
                promotion.setEndDate(endDate);
                promotion.setIsActive(true);
                return promotion;
            }).collect(Collectors.toList());*/

            /*List<PromotionProduct> promotionProducts = productIds.stream()
                    .map(productId ->{
                        PromotionProduct pp = new PromotionProduct();
                        pp.setPromotionId(promotionId);
                        pp.setProductId(productId);
                        pp.setDiscountRate(discountRate); // 使用统一的折扣率
                        return pp;
                    })
                    .collect(Collectors.toList());*/
// ========== 改变商品信息 ==========
            // 检查商品是否已经在其他促销中
            List<Product> alreadyPromoted = productService.lambdaQuery()
                    .in(Product::getProductId, productIds)
                    .eq(Product::getIsOnPromotion, true)
                    .list();

            if (!alreadyPromoted.isEmpty()) {
                String conflictIds = alreadyPromoted.stream()
                        .map(Product::getProductId)
                        .collect(Collectors.joining(","));
                return new createPromotionResponse("以下商品已参与其他促销: " + conflictIds);
            }

            // 计算并更新商品促销状态和价格
            List<Product> productsToUpdate = new ArrayList<>();
            for (Product product : existingProducts) {
                // 计算促销价
                BigDecimal currentPrice = calculateCurrentPrice(
                        product.getBasePrice(),
                        discountRate
                );

                // 更新商品信息
                product.setCurrentPrice(currentPrice);
                product.setIsOnPromotion(true);
                product.setUpdatedAt(LocalDateTime.now());
                productsToUpdate.add(product);
            }
            // ========== 改变商品信息 ==========
            if (!this.save(promotion)) {
                return new createPromotionResponse("保存促销信息失败");
            }


            List<PromotionProduct> promotionProducts = new ArrayList<>();
            for (String productId : productIds) {
                PromotionProduct item = new PromotionProduct();
                item.setPromotionId(promotionId);  // 联合主键字段1
                item.setProductId(productId);      // 联合主键字段2
                item.setDiscountRate(discountRate); // 业务字段
                promotionProducts.add(item);
            }


// 2. 批量保存（显式事务控制）
            try {
                if (!promotionProductService.saveBatch(promotionProducts)) {
                    return new createPromotionResponse("保存促销商品关联失败");
                }
            } catch (Exception e) {
                log.error("保存促销商品关联异常", e);
                return new createPromotionResponse("保存促销商品关联失败: " + e.getMessage());
            }

            try {
                if (!productService.updateBatchById(productsToUpdate)) {
                    return new createPromotionResponse("更新商品状态失败");
                }
            } catch (Exception e) {
                log.error("更新商品状态异常", e);
                return new createPromotionResponse("更新商品状态失败: " + e.getMessage());
            }

            return new createPromotionResponse(promotionId); // 成功返回
        } catch (DateTimeParseException e) {
            return new createPromotionResponse("日期格式不正确，应为yyyy-MM-dd HH");
        }
    }


    @Override
    public getPromotionResponse getPromotionById(String productId) {


        Optional<Product> product = productService.getProductById(productId);
        PromotionProduct promotionProduct = promotionProductMapper.selectByProductId(productId);
        if (promotionProduct == null) {
            throw new RuntimeException("该商品未参与促销");
        }
        Promotion promotion = promotionMapper.selectById(promotionProduct.getPromotionId());

        BigDecimal currentPrice = calculateCurrentPrice(
                product.get().getBasePrice(),
                promotionProduct.getDiscountRate()
        );

        return new getPromotionResponse(
                promotion.getPromotionId(),
                promotionProduct.getDiscountRate(),
                product.get().getBasePrice(),
                currentPrice,
                promotion.getEndDate()
        );

    }


    private BigDecimal calculateCurrentPrice(BigDecimal basePrice, BigDecimal discountRate) {
        // 参数校验
        if (basePrice == null || discountRate == null) {
            throw new IllegalArgumentException("价格和折扣率不能为空");
        }
        if (discountRate.compareTo(BigDecimal.ZERO) < 0 ||
                discountRate.compareTo(new BigDecimal("100")) > 0) {
            throw new IllegalArgumentException("折扣率必须在0-100之间");
        }

        // 计算公式：当前价格 = 基础价格 × (1 - 折扣率/100)
        return basePrice.multiply(
                BigDecimal.ONE.subtract(
                        discountRate.divide(new BigDecimal("100"), 10, RoundingMode.HALF_UP)
                )).setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public PromotionListResponse getActivePromotions(int page, int limit) {
        // 构建分页对象
        Page<Product> pageInfo = new Page<Product>(page, limit);

        LambdaQueryWrapper<Product> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Product::getIsOnPromotion, true);


        IPage<Product> products = productMapper.selectPage(pageInfo, queryWrapper);

        List<String> productIds = products.getRecords().stream()
                .map(Product::getProductId)
                .collect(Collectors.toList());

        List<PromotionProduct> promotionProducts = CollectionUtils.isEmpty(productIds)
                ? Collections.emptyList()
                : promotionProductMapper.selectList(
                new QueryWrapper<PromotionProduct>().in("product_id", productIds));


        PromotionListResponse response = new PromotionListResponse();
        response.setTotal((int) products.getTotal());

        List<PromotionListResponse.PromotionItem> promotions = products.getRecords().stream()
                .map(product -> {
                    // 确保product.getProductId()可用
                    PromotionProduct promotion = promotionProducts.stream()
                            .filter(pp -> pp.getProductId().equals(product.getProductId()))
                            .findFirst()
                            .orElse(null);

                    return convertToPromotionItem(product, promotion);
                })
                .collect(Collectors.toList());

        return response;
    }
    private PromotionListResponse.PromotionItem convertToPromotionItem(
            Product product, PromotionProduct promotionProduct) {

        PromotionListResponse.PromotionItem item = new PromotionListResponse.PromotionItem();
        item.setProductId(product.getProductId());
        item.setName(product.getName());

        if (promotionProduct != null) {
            // 计算当前价格
            BigDecimal discount = calculateCurrentPrice(product.getBasePrice(),promotionProduct.getDiscountRate());
        } else {
            // 如果没有找到促销信息，使用商品原价
            item.setCurrentPrice(product.getBasePrice());
            item.setDiscountRate(BigDecimal.ZERO);
            item.setValidUntil(LocalDateTime.now().plusDays(1)); // 默认有效期
        }

        return item;
    }

}