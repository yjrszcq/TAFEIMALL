package cn.edu.xidian.tafei_mall.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@TableName("bill")
@ApiModel(value = "Bill对象", description = "账单表")
public class Bill implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("账单ID")
    @TableId("bill_id")
    private String billId;

    @ApiModelProperty("用户ID")
    @TableField("user_id")
    private String userId;

    @ApiModelProperty("总金额")
    @TableField("total_amount")
    private BigDecimal totalAmount;

    @ApiModelProperty("支付方式")
    @TableField("payment_method")
    private String paymentMethod;

    @ApiModelProperty("账单状态")
    @TableField("status")
    private String status;

    @ApiModelProperty("创建时间")
    @TableField("created_at")
    private LocalDateTime createdAt;

    @ApiModelProperty("支付时间")
    @TableField("paid_at")
    private LocalDateTime paidAt;
}