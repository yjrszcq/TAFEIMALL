package cn.edu.xidian.tafei_mall.model.vo.Response.Auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegisterResponse {
    private final String userId;
    private final String message;

    public RegisterResponse(String userId, String message) {
        this.userId = userId;
        this.message = message;
    }
}
