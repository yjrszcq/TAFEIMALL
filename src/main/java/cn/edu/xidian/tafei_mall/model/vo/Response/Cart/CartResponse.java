package cn.edu.xidian.tafei_mall.model.vo.Response.Cart;

import cn.edu.xidian.tafei_mall.model.entity.CartItem;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.List;


@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CartResponse {
    private final String cartId;
    private final List<CartItem> items;
    private final double total;

    public CartResponse(String cartId, List<CartItem> items, double total) {
        this.cartId = cartId;
        this.items = items;
        this.total = total;
    }
}
