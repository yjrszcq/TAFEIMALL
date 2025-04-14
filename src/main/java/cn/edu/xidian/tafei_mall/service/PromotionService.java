package cn.edu.xidian.tafei_mall.service;

import cn.edu.xidian.tafei_mall.model.vo.PromotionCreateVO;
import cn.edu.xidian.tafei_mall.model.vo.Response.Promotion.createPromotionResponse;

public interface PromotionService {
    createPromotionResponse createPromotion(PromotionCreateVO promotionCreateVO);
    // List<Promotion> getAllPromotions();
    // boolean deletePromotionById(String id);
}
