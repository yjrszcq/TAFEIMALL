package cn.edu.xidian.tafei_mall.model.vo.Response.Order;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderItemResponse {
    private final String orderItemId;
    private final String orderId;
    private final String product;
    private final Integer quantity;
    private final BigDecimal price;

    public OrderItemResponse(String orderItemId, String orderId, String product, Integer quantity, BigDecimal price) {
        this.orderItemId = orderItemId;
        this.orderId = orderId;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
    }
}
