package cn.edu.xidian.tafei_mall.service.impl;

import cn.edu.xidian.tafei_mall.mapper.PromotionMapper;
import cn.edu.xidian.tafei_mall.model.entity.Promotion;
import cn.edu.xidian.tafei_mall.model.vo.PromotionCreateVO;
import cn.edu.xidian.tafei_mall.model.vo.Response.Promotion.createPromotionResponse;
import cn.edu.xidian.tafei_mall.service.PromotionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PromotionServiceImpl extends ServiceImpl<PromotionMapper, Promotion> implements PromotionService {
    @Autowired
    private PromotionMapper promotionMapper;

    @Override
    public createPromotionResponse createPromotion(PromotionCreateVO promotionCreateVO){
        return new createPromotionResponse("");
    }
}
