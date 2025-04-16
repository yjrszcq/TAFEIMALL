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
    public void createPayOrder(String orderId, String userId) {
        //获得订单信息
        Order order = orderService.getOrderById(orderId);
        if (order == null) {
            throw new RuntimeException("订单不存在");
        }
        if (!order.getUserId().equals(userId)) {
            throw new RuntimeException("订单不属于当前用户");
        }
        // 这里可以对接支付平台API完成支付 TODO
        // 监听支付状态
        if (true) {
            orderService.updateOrderStatus(order.getOrderId(), "paid");
        }
    }
}
