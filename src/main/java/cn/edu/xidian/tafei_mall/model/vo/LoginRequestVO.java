package cn.edu.xidian.tafei_mall.model.vo;


import lombok.Data;

@Data
public class LoginRequestVO {
    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    // Getters and Setters
}