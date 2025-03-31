package cn.edu.xidian.tafei_mall.service;

import cn.edu.xidian.tafei_mall.model.entity.Order;
import cn.edu.xidian.tafei_mall.model.vo.OrderCreateVO;
<<<<<<< HEAD
import cn.edu.xidian.tafei_mall.model.vo.Response.Order.getOrderResponse;
=======
import cn.edu.xidian.tafei_mall.model.vo.OrderUpdateVO;
import cn.edu.xidian.tafei_mall.model.vo.Response.Buyer.createOrderBuyerResponse;
import cn.edu.xidian.tafei_mall.model.vo.Response.Buyer.getOrderBuyerResponse;
import cn.edu.xidian.tafei_mall.model.vo.Response.Seller.updateOrderResponse;
>>>>>>> upstream/dev
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
<<<<<<< HEAD
    getOrderResponse getOrderByCustomer(String userId);
    getOrderResponse getOrderByCustomer(String OrderId, String userId);
    String createOrder(String cartId, OrderCreateVO orderCreateVO, String userId);
    boolean cancelOrder(String orderId, String userId);
    // 卖家
    getOrderResponse getOrderBySeller(String userId);
=======
    getOrderBuyerResponse getOrderByCustomer(String userId);
    getOrderBuyerResponse getOrderByCustomer(String OrderId, String userId);
    createOrderBuyerResponse createOrder(String cartId, OrderCreateVO orderCreateVO, String userId);
    boolean cancelOrder(String orderId, String userId);
    // 卖家
    updateOrderResponse updateOrderBySeller(String orderId, OrderUpdateVO orderUpdateV, String userId);
>>>>>>> upstream/dev
}