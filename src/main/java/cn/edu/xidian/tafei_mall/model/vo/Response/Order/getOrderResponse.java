package cn.edu.xidian.tafei_mall.model.vo.Response.Order;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class getOrderResponse {
    private final List<Order> orders = new ArrayList<>();

    public getOrderResponse(List<cn.edu.xidian.tafei_mall.model.entity.Order> orders) {
        for (cn.edu.xidian.tafei_mall.model.entity.Order order : orders) {
            this.orders.add(new Order(order.getOrderId(), order.getUserId(), order.getTotalAmount(), order.getPaymentMethod(), order.getShippingAddressId(), order.getStatus()));
        }
    }
}

@Getter
class Order {
    private final String orderId;
    private final String userId;
    private final BigDecimal totalAmount;
    private final String paymentMethod;
    private final String shippingAddressId;
    private final String status;

    public Order(String orderId, String userId, BigDecimal totalAmount, String paymentMethod, String shippingAddressId, String status) {
        this.orderId = orderId;
        this.userId = userId;
        this.totalAmount = totalAmount;
        this.paymentMethod = paymentMethod;
        this.shippingAddressId = shippingAddressId;
        this.status = status;
    }
}
