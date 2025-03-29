package cn.edu.xidian.tafei_mall.model.vo.Response.Address;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class getAddressResponse {
    private final List<Item> address;

    public getAddressResponse() {
        address = new ArrayList<>();
    }

    public void addAddress(String addressId, String address, String city, String postalCode) {
        this.address.add(new Item(addressId, address, city, postalCode));
    }
}

@Setter
@Getter
class Item {
    private final String addressId;
    private final String address;
    private final String city;
    private final String postalCode;

    public Item(String addressId, String address, String city, String postalCode) {
        this.addressId = addressId;
        this.address = address;
        this.city = city;
        this.postalCode = postalCode;
    }
}