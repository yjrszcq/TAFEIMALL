package cn.edu.xidian.tafei_mall.model.vo.Response.Promotion;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel("有效促销列表响应")
    @Data
    public class PromotionListResponse {
        private Integer total;
        private List<PromotionItem> promotions;

        @Data
        public static class PromotionItem {
            private String productId;
            private String name;
            private BigDecimal discountRate;
            private BigDecimal currentPrice;
            private LocalDateTime validUntil;
        }
    }