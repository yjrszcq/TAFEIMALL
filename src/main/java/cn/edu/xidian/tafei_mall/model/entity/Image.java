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

/**
 * * <p>
 *     * 图片表
 * * </p>
 *
 * @author shenyaoguan
 *
 * @since 2025-03-17
 */

@Getter
@Setter
@TableName("t_image")
@ApiModel(value = "Image对象", description = "图片表")
public class Image implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("图片ID")
    @TableId("image_id")
    private String imageId;

    @ApiModelProperty("图片URL")
    @TableField("image_path")
    private String imagePath;

    @ApiModelProperty("图片所属商品ID")
    @TableField("product_id")
    private String productId;

    @ApiModelProperty("创建时间")
    @TableField("created_at")
    private String createdAt;

    @ApiModelProperty("更新时间")
    @TableField("updated_at")
    private String updatedAt;
}
