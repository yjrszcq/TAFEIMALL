package cn.edu.xidian.tafei_mall.model.vo.Response.Address;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddressResponse {
    private final String message;

    public AddressResponse(String message) {
        this.message = message;
    }
}
