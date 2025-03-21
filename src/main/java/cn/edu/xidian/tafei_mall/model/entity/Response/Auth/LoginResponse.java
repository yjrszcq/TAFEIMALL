package cn.edu.xidian.tafei_mall.model.entity.Response.Auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginResponse {
    @Getter
    private String sessionId;
    @Getter
    private String userId;
    @Getter
    private String userAccount;

    public LoginResponse(String sessionId, String userId, String userAccount) {
        this.sessionId = sessionId;
        this.userId = userId;
        this.userAccount = userAccount;
    }
}
