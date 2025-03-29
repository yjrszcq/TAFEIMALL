package cn.edu.xidian.tafei_mall.service;

import cn.edu.xidian.tafei_mall.model.entity.Order;
import cn.edu.xidian.tafei_mall.model.vo.OrderCreateVO;
import cn.edu.xidian.tafei_mall.model.vo.Response.Order.getOrderRespnose;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 订单表 服务类
 * </p>
 *
 * @author shenyaoguan
 * @since 2025-03-17
 */
public interface OrderService extends IService<Order> {
    getOrderRespnose getOrderById(String OrderId, String userId);
    String createOrder(String cartId, OrderCreateVO orderCreateVO, String userId);
    boolean cancelOrder(String orderId, String userId);

    getOrderRespnose getOrderBySeller(String userId);
}