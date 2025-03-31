package cn.edu.xidian.tafei_mall.model.vo;

import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class OrderUpdateVO {
    private String action;
    private String trackingNumber;
}
