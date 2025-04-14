package cn.edu.xidian.tafei_mall.model.vo;

import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class PromotionCreateVO {
    private String productId;
    private double discountRate;
    private String startDate;
    private String endDate;
}
