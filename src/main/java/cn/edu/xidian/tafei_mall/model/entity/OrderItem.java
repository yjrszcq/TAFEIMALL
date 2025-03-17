package cn.edu.xidian.tafei_mall.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 订单项表
 * </p>
 *
 * @author shenyaoguan
 * @since 2025-03-17
 */
@Getter
@Setter
@TableName("order_item")
@ApiModel(value = "OrderItem对象", description = "订单项表")
public class OrderItem implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("订单项ID")
    @TableId("order_item_id")
    private String orderItemId;

    @ApiModelProperty("订单ID")
    @TableField("order_id")
    private String orderId;

    @ApiModelProperty("商品ID")
    @TableField("product_id")
    private String productId;

    @ApiModelProperty("数量")
    @TableField("quantity")
    private Integer quantity;

    @ApiModelProperty("单价")
    @TableField("price")
    private BigDecimal price;

    @ApiModelProperty("创建时间")
    @TableField("created_at")
    private LocalDateTime createdAt;

    @ApiModelProperty("更新时间")
    @TableField("updated_at")
    private LocalDateTime updatedAt;
}
