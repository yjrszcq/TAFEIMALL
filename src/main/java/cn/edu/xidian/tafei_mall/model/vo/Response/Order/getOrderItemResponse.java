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
public class getOrderItemResponse {
    private final List<OrderItem> orderItems = new ArrayList<>();
    public getOrderItemResponse(List<cn.edu.xidian.tafei_mall.model.entity.OrderItem> orderItems) {
        for (cn.edu.xidian.tafei_mall.model.entity.OrderItem orderItem : orderItems) {
            this.orderItems.add(new OrderItem(orderItem.getOrderItemId(), orderItem.getOrderId(), orderItem.getProductId(), orderItem.getQuantity(), orderItem.getPrice()));
        }
    }
}


@Getter
class OrderItem{
    private final String OrderItemId;
    private final String OrderId;
    private final String ProductId;
    private final int Quantity;
    private final BigDecimal Price;

    public OrderItem(String OrderItemId, String OrderId, String ProductId, int Quantity, BigDecimal Price) {
        this.OrderItemId = OrderItemId;
        this.OrderId = OrderId;
        this.ProductId = ProductId;
        this.Quantity = Quantity;
        this.Price = Price;
    }
}