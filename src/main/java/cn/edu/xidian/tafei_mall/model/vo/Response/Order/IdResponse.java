package cn.edu.xidian.tafei_mall.model.vo.Response.Order;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class IdResponse {
    private final String orderId;

    public IdResponse(String orderId) {
        this.orderId = orderId;
    }
}