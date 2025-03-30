package cn.edu.xidian.tafei_mall.model.vo.Response.Order;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderIdResponse {
    private final String orderId;

    public OrderIdResponse(String orderId) {
        this.orderId = orderId;
    }
}