package cn.edu.xidian.tafei_mall.model.vo.Response.Address;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class getAddressResponse {
    private final String addressId;
    private final String address;
    private final String phone;
    private final String name;


    public getAddressResponse() {
        addressId = null;
        address = null;
        phone = null;
        name = null;
    }
}
