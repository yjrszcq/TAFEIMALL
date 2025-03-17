package cn.edu.xidian.tafei_mall.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 会话表
 * </p>
 *
 * @author shenyaoguan
 * @since 2025-03-17
 */
@Getter
@Setter
@TableName("session")
@ApiModel(value = "Session对象", description = "会话表")
public class Session implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("会话ID")
    @TableId("session_id")
    private String sessionId;

    @ApiModelProperty("用户ID")
    @TableField("user_id")
    private String userId;

    @ApiModelProperty("会话令牌")
    @TableField("session_token")
    private String sessionToken;

    @ApiModelProperty("过期时间")
    @TableField("expires_at")
    private LocalDateTime expiresAt;

    @ApiModelProperty("创建时间")
    @TableField("created_at")
    private LocalDateTime createdAt;
}
