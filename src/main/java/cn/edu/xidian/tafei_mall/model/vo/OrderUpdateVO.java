package cn.edu.xidian.tafei_mall.model.vo;

import cn.hutool.core.annotation.Alias;
import lombok.Data;

@Data
public class OrderUpdateVO {

    @Alias("status")
    private String action;
    // private String trackingNumber;
}
