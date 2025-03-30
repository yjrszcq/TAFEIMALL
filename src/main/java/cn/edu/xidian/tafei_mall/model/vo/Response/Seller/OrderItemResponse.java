package cn.edu.xidian.tafei_mall.model.vo.Response.Seller;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderItemResponse {
    private final String productId;
    private final String productName;
    private final String orderId;
    private final BigDecimal price;
    private final String itemId;

    public OrderItemResponse(String productId, String productName, String orderId, BigDecimal price, String itemId) {
        this.productId = productId;
        this.productName = productName;
        this.orderId = orderId;
        this.price = price;
        this.itemId = itemId;
    }
}
