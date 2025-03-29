package cn.edu.xidian.tafei_mall.model.vo.Response.Order;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDetailResponse {
    private final String orderId;
    private final String status;
    private final getOrderItemResponse items;

    public OrderDetailResponse(String orderId, String status, getOrderItemResponse items) {
        this.orderId = orderId;
        this.status = status;
        this.items = items;
    }
}
