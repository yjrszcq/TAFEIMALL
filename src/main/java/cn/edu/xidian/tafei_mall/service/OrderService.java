package cn.edu.xidian.tafei_mall.service;

import cn.edu.xidian.tafei_mall.model.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 订单表 服务类
 * </p>
 *
 * @author shenyaoguan
 * @since 2025-03-17
 */
public interface OrderService extends IService<Order> {
    List<Order> getOrderById(String sessionId, String OrderId);
    List<Order> getOrderByAdminById(String sessionId);
    String createOrder(String cartId, String addressId);
    Order updateOrderStatus(String orderId, Map<String, String> status);
    boolean cancelOrder(String orderId);
}
