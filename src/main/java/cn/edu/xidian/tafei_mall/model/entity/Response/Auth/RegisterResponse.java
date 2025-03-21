package cn.edu.xidian.tafei_mall.model.entity.Response.Auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegisterResponse {
    @Getter
    private String userId;
    @Getter
    private String message;

    public RegisterResponse(String userId, String message) {
        this.userId = userId;
        this.message = message;
    }
}
