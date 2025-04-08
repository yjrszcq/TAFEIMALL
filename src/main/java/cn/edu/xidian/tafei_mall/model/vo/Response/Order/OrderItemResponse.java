package cn.edu.xidian.tafei_mall.model.vo.Response.Order;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderItemResponse {
    private final String orderItemId;
    private final String productId;
    private final String productName;
    private final Integer quantity;
    private final BigDecimal price;

    public OrderItemResponse(String orderItemId, String productId, String productName, Integer quantity, BigDecimal price) {
        this.orderItemId = orderItemId;
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }
}
