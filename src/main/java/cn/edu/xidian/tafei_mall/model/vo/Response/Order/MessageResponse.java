package cn.edu.xidian.tafei_mall.model.vo.Response.Order;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageResponse {
    private final String message;

    public MessageResponse(String message) {
        this.message = message;
    }
}
