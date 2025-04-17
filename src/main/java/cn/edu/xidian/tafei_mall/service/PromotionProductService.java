package cn.edu.xidian.tafei_mall.service;

import cn.edu.xidian.tafei_mall.model.entity.PromotionProduct;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigDecimal;
import java.util.List;

public interface PromotionProductService extends IService<PromotionProduct> {
    boolean savePromotionProducts(String promotionId, List<String> productIds, BigDecimal discountRate);

    /**
     * 根据促销ID获取关联商品
     */
    List<PromotionProduct> getByPromotionId(String promotionId);
}
