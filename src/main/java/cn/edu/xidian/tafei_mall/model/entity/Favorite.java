package cn.edu.xidian.tafei_mall.model.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.time.LocalDateTime;

@Getter
@Setter
@TableName("t_user_favorites")
@ApiModel(value = "UserFavorites对象", description = "用户收藏表")
public class Favorite {
    @Serial
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户ID")
    @MppMultiId
    @TableField("user_id") // 组合主键的一部分
    private String userId;

    @ApiModelProperty("商品ID")
    @MppMultiId
    @TableField("product_id") // 组合主键的另一部分
    private String productId;

    @ApiModelProperty("创建时间")
    @TableField("created_at")
    private LocalDateTime createdAt;
}