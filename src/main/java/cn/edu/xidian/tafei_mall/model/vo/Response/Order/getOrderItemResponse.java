package cn.edu.xidian.tafei_mall.model.vo.Response.Order;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class getOrderItemResponse {
    private final List<OrderItemResponse> items;

    public getOrderItemResponse(List<OrderItemResponse> items) {
        this.items = items;
    }
}
