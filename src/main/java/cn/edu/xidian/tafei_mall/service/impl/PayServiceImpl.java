package cn.edu.xidian.tafei_mall.service.impl;


import cn.edu.xidian.tafei_mall.model.entity.Order;
import cn.edu.xidian.tafei_mall.service.OrderService;
import cn.edu.xidian.tafei_mall.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PayServiceImpl implements PayService {
    @Autowired
    private OrderService orderService;


    @Override
    public boolean queryOrderStatus(String orderId) {
        return "paid".equals(orderService.getOrderById(orderId).getStatus());
    }

    @Override
    public void createPayOrder(String orderId) {
        //获得订单信息
        Order order = orderService.getOrderById(orderId);
        // 这里可以调用订单服务来创建订单 TODO
        // 监听支付状态
        if (true) {
            order.setStatus("paid");
            orderService.updateById(order);
        }
    }
}
