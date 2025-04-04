package cn.edu.xidian.tafei_mall.service;


public interface PayService{
    boolean queryOrderStatus(String orderId);

    void createPayOrder(String orderId);
}
