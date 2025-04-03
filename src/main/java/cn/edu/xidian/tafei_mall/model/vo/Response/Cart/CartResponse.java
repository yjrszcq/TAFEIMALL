package cn.edu.xidian.tafei_mall.model.vo.Response.Cart;

//import cn.edu.xidian.tafei_mall.model.entity.CartItem;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;


@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CartResponse {
    private final String cartId;
    private final List<CartItem> items;
    private double total;

    public CartResponse(String cartId) {
        this.cartId = cartId;
        this.items = new ArrayList<>();
        this.total = 0;
    }
    public boolean putItem(CartItem item) {
        if (items.contains(item)) {
            return false;
        }
        items.add(item);
        return true;
    }
    
    public boolean putItem (String itemId, String productId, String name, int quantity, double price) {
        CartItem newItem = new CartItem(itemId, productId, name, quantity, price);
        return putItem(newItem);
    }

    public boolean setTotal(double total) {
        if (total < 0) {
            return false;
        }
        this.total = total;
        return true;
    }
}


@Getter
class CartItem {
    private final String itemId;
    private final String productId;
    private final String name;
    private final int quantity;
    private final double price;
    
    public CartItem(String itemId, String productId, String name, int quantity, double price) {
        this.itemId = itemId;
        this.productId = productId;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }
}