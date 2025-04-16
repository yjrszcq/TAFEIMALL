package cn.edu.xidian.tafei_mall.model.vo.Response.Promotion;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
/*public class createPromotionResponse {
    private String promotionId;

    public createPromotionResponse(String promotionId) {
        this.promotionId = promotionId;
    }
}*/
public class createPromotionResponse {
    private List<String> promotionIds;
    private String errorMessage;

    public createPromotionResponse(List<String> promotionIds) {
        this.promotionIds = promotionIds;
    }

    public createPromotionResponse(String errorMessage) {
        this.promotionIds = null;
        this.errorMessage = errorMessage;
    }
}