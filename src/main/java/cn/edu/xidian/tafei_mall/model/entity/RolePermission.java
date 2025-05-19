package cn.edu.xidian.tafei_mall.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RolePermission {
    private Address address;
    private Cart cart;
    private Order order;
    private Review review;
    private Favorite favorite;
    private product product;
    private Promotion promotion;
    private Report report;

    @Setter
    @Getter
    public static class Address{
        private boolean get;
        private boolean edit;
    }

    @Setter
    @Getter
    public static class Cart{
        private boolean get;
        private boolean edit;
    }

    @Setter
    @Getter
    public static class Order{
        private boolean get;
        private boolean create;
        private boolean pay;
        private boolean ship;
        private boolean finish;
        private boolean cancel;
        private boolean delete;
        private boolean update;
    }

    @Setter
    @Getter
    public static class Review{
        private boolean edit;
        private boolean delete;
    }

    @Setter
    @Getter
    public static class Favorite{
        private boolean get;
        private boolean edit;
        private boolean delete;
    }

    @Setter
    @Getter
    public static class product{
        private boolean edit;
        private boolean delete;
    }

    @Setter
    @Getter
    public static class Promotion{
        private boolean edit;
        private boolean delete;
    }

    @Setter
    @Getter
    public static class Report{
        private boolean generate;
    }
}