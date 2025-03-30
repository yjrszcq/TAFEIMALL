package cn.edu.xidian.tafei_mall.model.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OrderUpdateVO {
    @JsonProperty("action")
    private String status;

    private String trackingNumber;
}
