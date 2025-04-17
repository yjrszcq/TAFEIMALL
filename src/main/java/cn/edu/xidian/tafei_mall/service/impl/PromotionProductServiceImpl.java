package cn.edu.xidian.tafei_mall.service.impl;

import cn.edu.xidian.tafei_mall.mapper.PromotionProductMapper;
import cn.edu.xidian.tafei_mall.model.entity.PromotionProduct;
import cn.edu.xidian.tafei_mall.service.PromotionProductService;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PromotionProductServiceImpl extends ServiceImpl<PromotionProductMapper, PromotionProduct>
        implements PromotionProductService {

    @Override
    public boolean savePromotionProducts(String promotionId, List<String> productIds, BigDecimal discountRate) {
        if (CollectionUtils.isEmpty(productIds) || discountRate == null) {
            return false;
        }

        List<PromotionProduct> list = productIds.stream()
                .map(productId -> {
                    PromotionProduct item = new PromotionProduct();
                    item.setPromotionId(promotionId);  // 联合主键字段1
                    item.setProductId(productId);      // 联合主键字段2
                    item.setDiscountRate(discountRate); // 普通字段
                    return item;
                }) .collect(Collectors.toList());

        return this.saveBatch(list);
    }

    @Override
    public List<PromotionProduct> getByPromotionId(String promotionId) {
        return this.lambdaQuery()
                .eq(PromotionProduct::getPromotionId, promotionId)
                .list();
    }
}