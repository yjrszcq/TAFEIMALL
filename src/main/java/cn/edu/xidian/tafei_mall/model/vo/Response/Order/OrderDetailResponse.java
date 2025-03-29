package cn.edu.xidian.tafei_mall.model.vo.Response.Order;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDetailResponse {
    private final String orderId;
    private final String userId;
    // private final String sellerId;
    private final BigDecimal totalAmount;
    private final String paymentMethod;
    private final String shippingAddressId;
    private final String status;
    private final getOrderItemResponse items;

    public OrderDetailResponse(String orderId, String userId, /*String sellerId, */BigDecimal totalAmount, String paymentMethod, String shippingAddressId, String status, getOrderItemResponse items) {
        this.orderId = orderId;
        this.userId = userId;
        // this.sellerId = sellerId;
        this.totalAmount = totalAmount;
        this.paymentMethod = paymentMethod;
        this.shippingAddressId = shippingAddressId;
        this.status = status;
        this.items = items;
    }
}
