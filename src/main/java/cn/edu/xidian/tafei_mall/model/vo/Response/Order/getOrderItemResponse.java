package cn.edu.xidian.tafei_mall.model.vo.Response.Order;

import cn.edu.xidian.tafei_mall.model.entity.OrderItem;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class getOrderItemResponse {
    private final List<OrderItemResponse> items = new ArrayList<>();

    public getOrderItemResponse(List<OrderItem> items) {
        for (OrderItem item : items) {
            this.items.add(new OrderItemResponse(item.getOrderItemId(), item.getOrderId(), item.getProductId(), item.getQuantity(), item.getPrice()));
        }
    }
}
