package cn.edu.xidian.tafei_mall.model.vo.Response.Buyer;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class createOrderBuyerResponse {
    private final List<String> orderIds;

    public createOrderBuyerResponse(List<String> orderIds) {
        this.orderIds = orderIds;
    }
}
