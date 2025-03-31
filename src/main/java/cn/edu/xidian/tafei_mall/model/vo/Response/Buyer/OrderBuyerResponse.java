package cn.edu.xidian.tafei_mall.model.vo.Response.Buyer;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderBuyerResponse {
    private final String orderId;
    private final String status;
    private final getOrderItemBuyerResponse items;

    public OrderBuyerResponse(String orderId, String status, getOrderItemBuyerResponse items) {
        this.orderId = orderId;
        this.status = status;
        this.items = items;
    }
}
