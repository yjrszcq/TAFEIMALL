package cn.edu.xidian.tafei_mall.model.vo.Response.Favorite;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FavoriteResponse {
    private final String productId;
    private final String name;
    private final BigDecimal currentPrice;
    private final String thumbnail;

    public FavoriteResponse(String productId, String name, BigDecimal currentPrice, String thumbnail) {
        this.productId = productId;
        this.name = name;
        this.currentPrice = currentPrice;
        this.thumbnail = thumbnail;
    }
}
