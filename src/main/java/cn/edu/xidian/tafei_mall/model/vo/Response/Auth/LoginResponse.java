package cn.edu.xidian.tafei_mall.model.vo.Response.Auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginResponse {
    private final String sessionId;
    private final String userId;
    private final String userAccount;

    public LoginResponse(String sessionId, String userId, String userAccount) {
        this.sessionId = sessionId;
        this.userId = userId;
        this.userAccount = userAccount;
    }
}
