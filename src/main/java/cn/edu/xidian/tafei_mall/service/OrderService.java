package cn.edu.xidian.tafei_mall.service;

import cn.edu.xidian.tafei_mall.model.entity.Order;
import cn.edu.xidian.tafei_mall.model.vo.OrderCreateVO;
import cn.edu.xidian.tafei_mall.model.vo.Response.Order.getOrderResponse;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单表 服务类
 * </p>
 *
 * @author shenyaoguan
 * @since 2025-03-17
 */
public interface OrderService extends IService<Order> {
    // Server层中，同层调用，不需要暴露给上层
    Order getOrderById(String orderId);
    // 买家
    getOrderResponse getOrderByCustomer(String userId);
    getOrderResponse getOrderByCustomer(String OrderId, String userId);
    String createOrder(String cartId, OrderCreateVO orderCreateVO, String userId);
    boolean cancelOrder(String orderId, String userId);
    // 卖家
    getOrderResponse getOrderBySeller(String userId);
}