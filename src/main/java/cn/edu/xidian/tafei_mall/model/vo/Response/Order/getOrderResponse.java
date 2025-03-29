package cn.edu.xidian.tafei_mall.model.vo.Response.Order;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class getOrderResponse {
    private final List<OrderDetailResponse> orders;

    public getOrderResponse(List<OrderDetailResponse> orders) {
        this.orders = orders;
    }
}
