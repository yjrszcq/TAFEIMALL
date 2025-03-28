package cn.edu.xidian.tafei_mall.service;

import cn.edu.xidian.tafei_mall.model.entity.OrderItem;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 订单项表 服务类
 * </p>
 *
 * @author shenyaoguan
 * @since 2025-03-17
 */
public interface OrderItemService extends IService<OrderItem> {
    List<OrderItem> getOrderItemsByOrderId(String sessionId, String orderId);
    List<OrderItem> getOrderItemsByAdminByOrderId(String orderItemId);
    OrderItem getOrderItemById(String sessionId, String orderItemId);
    OrderItem getOrderItemByAdminById(String orderItemId);
}
