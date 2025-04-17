package cn.edu.xidian.tafei_mall.mapper;

import cn.edu.xidian.tafei_mall.model.entity.PromotionProduct;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PromotionProductMapper extends BaseMapper<PromotionProduct> {
    @Results(id = "promotionProductMap", value = {
            @Result(column = "promotion_id", property = "promotionId"),
            @Result(column = "product_id", property = "productId", id = true), // 标记为ID
            @Result(column = "discount_rate", property = "discountRate")
    })
    @Select("SELECT * FROM t_promotion_products WHERE promotion_id = #{promotionId}")
    List<PromotionProduct> selectByPromotionId(String promotionId);

    @Select("SELECT * FROM t_promotion_products WHERE product_id = #{productId}")
    PromotionProduct selectByProductId(@Param("productId") String productId);
}
