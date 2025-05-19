package cn.edu.xidian.tafei_mall.model.vo;

import lombok.Data;



@Data
public class UserRegistrationVO {
    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 邮箱
     */
    private String email;

    private String phone;

    private String roleId;
}