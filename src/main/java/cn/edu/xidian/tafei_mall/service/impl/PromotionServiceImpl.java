package cn.edu.xidian.tafei_mall.service.impl;

import cn.edu.xidian.tafei_mall.mapper.PromotionMapper;
import cn.edu.xidian.tafei_mall.model.entity.Promotion;
import cn.edu.xidian.tafei_mall.model.vo.PromotionCreateVO;
import cn.edu.xidian.tafei_mall.model.vo.Response.Promotion.createPromotionResponse;
import cn.edu.xidian.tafei_mall.model.vo.Response.Promotion.getPromotionResponse;
import cn.edu.xidian.tafei_mall.service.ProductService;
import cn.edu.xidian.tafei_mall.service.PromotionService;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.edu.xidian.tafei_mall.model.entity.Product;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class PromotionServiceImpl extends ServiceImpl<PromotionMapper, Promotion> implements PromotionService {

    @Autowired
    private PromotionMapper promotionMapper;

    @Autowired
    private ProductService productService;

    @Override
    public createPromotionResponse createPromotion(PromotionCreateVO promotionCreateVO){

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

        Double discountRate = promotionCreateVO.getDiscountRate();
        if (discountRate <= 0 || discountRate > 100) {
            return new createPromotionResponse("折扣率必须大于0且小于等于100");
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

            List<Promotion> promotions = productIds.stream().map(productId -> {
                Promotion promotion = new Promotion();
                promotion.setPromotionId(UUID.randomUUID().toString());
                promotion.setProductId(productId);
                promotion.setDiscountRate(Double.toString(discountRate));
                promotion.setStartDate(startDate);
                promotion.setEndDate(endDate);
                promotion.setIsActive(true);
                return promotion;
            }).collect(Collectors.toList());

            if (saveBatch(promotions)) {
                return new createPromotionResponse(
                        promotions.stream()
                                .map(Promotion::getPromotionId)
                                .collect(Collectors.toList())
                );
            } else {
                return new createPromotionResponse("创建促销活动失败");
            }


        } catch (Exception e) {
            log.error("创建促销活动异常", e);
            return new createPromotionResponse("系统异常: " + e.getMessage());
        }
    }



    @Override
    public getPromotionResponse getPromotionById(String productId) {

        Promotion promotion = promotionMapper.selectById(productId);
        Optional<Product> product = productService.getProductById(productId);

        if (promotion == null) {
            throw new RuntimeException("无相关促销");
        }

        BigDecimal currentPrice = calculateCurrentPrice(
                product.get().getBasePrice(),
                Double.parseDouble(promotion.getDiscountRate())
        );

        return new getPromotionResponse(
                promotion.getPromotionId(),
                Double.parseDouble(promotion.getDiscountRate()),//Entity promotion里discountRate类型是String
                product.get().getBasePrice(),
                currentPrice,
                promotion.getEndDate()
        );

    }


    private BigDecimal calculateCurrentPrice(BigDecimal basePrice, Double discountRate) {
        return basePrice.multiply(
                BigDecimal.ONE.subtract(
                        BigDecimal.valueOf(discountRate).divide(BigDecimal.valueOf(100))
                ).setScale(2, RoundingMode.HALF_UP));
    }



}
