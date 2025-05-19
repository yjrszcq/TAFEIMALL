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
import java.time.LocalDateTime;

@Getter
@Setter
@TableName("permission")
@ApiModel(value = "Permission对象", description = "权限表")
public class Permission implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("权限ID")
    @TableId("permission_id")
    private String permissionId;

    @ApiModelProperty("购物车管理权限")
    @TableField("cart_permission")
    private int cartPermission;

    @ApiModelProperty("订单管理权限")
    @TableField("order_permission")
    private int orderPermission;

    @ApiModelProperty("商品管理权限")
    @TableField("product_permission")
    private int productPermission;

    @ApiModelProperty("地址管理权限")
    @TableField("address_permission")
    private int addressPermission;

    @ApiModelProperty("评论管理权限")
    @TableField("review_permission")
    private int reviewPermission;

    @ApiModelProperty("收藏管理权限")
    @TableField("favorite_permission")
    private int favoritePermission;

    @ApiModelProperty("角色管理权限")
    @TableField("role_permission")
    private int rolePermission;

    @ApiModelProperty("用户管理权限")
    @TableField("user_permission")
    private int userPermission;

    @ApiModelProperty("创建时间")
    @TableField("created_at")
    private LocalDateTime createdAt;

    @ApiModelProperty("更新时间")
    @TableField("updated_at")
    private LocalDateTime updatedAt;
}