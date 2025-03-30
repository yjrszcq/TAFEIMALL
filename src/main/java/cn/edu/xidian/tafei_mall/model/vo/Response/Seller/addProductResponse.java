package cn.edu.xidian.tafei_mall.model.vo.Response.Seller;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class addProductResponse {
    private final String productId;

    public addProductResponse(String productId) {
        this.productId = productId;
    }
}
