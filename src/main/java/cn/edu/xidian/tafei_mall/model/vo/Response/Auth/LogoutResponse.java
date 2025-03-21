package cn.edu.xidian.tafei_mall.model.vo.Response.Auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LogoutResponse {
    @Getter
    private String message;

    public LogoutResponse(String message) {
        this.message = message;
    }
}
