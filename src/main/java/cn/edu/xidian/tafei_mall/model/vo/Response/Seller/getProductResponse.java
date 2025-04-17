package cn.edu.xidian.tafei_mall.model.vo.Response.Seller;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class getProductResponse {
    private final List<Product> products = new ArrayList<>();

    public getProductResponse(List<cn.edu.xidian.tafei_mall.model.entity.Product> products) {
        for (cn.edu.xidian.tafei_mall.model.entity.Product product : products) {
            this.products.add(new Product(product.getProductId(), product.getName(), product.getDescription(), product.getBasePrice(), product.getStock(), product.getIsFreeShipping()));
        }
    }
}

@Getter
class Product{
    private final String productId;
    private final String name;
    private final String description;
    private final String price;
    private final String stock;
    private final Boolean is_free_shipping;

    public Product(String productId, String name, String description, BigDecimal price, Integer stock, Boolean isFreeShipping) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.price = price.toString();
        this.stock = stock.toString();
        this.is_free_shipping = isFreeShipping;
    }
}