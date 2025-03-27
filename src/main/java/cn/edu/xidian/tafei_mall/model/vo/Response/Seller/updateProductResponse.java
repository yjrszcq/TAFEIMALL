package cn.edu.xidian.tafei_mall.model.vo.Response.Seller;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class updateProductResponse {
    private final String result;

    public updateProductResponse(String result) {
        this.result = result;
    }
}
