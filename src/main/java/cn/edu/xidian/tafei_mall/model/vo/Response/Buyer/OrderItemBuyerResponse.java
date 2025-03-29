package cn.edu.xidian.tafei_mall.model.vo.Response.Buyer;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderItemBuyerResponse {
    private final String productId;
    private final String productName;
    private final Integer quantity;
    private final BigDecimal price;

    public OrderItemBuyerResponse(String productId, String productName, Integer quantity, BigDecimal price) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }
}
