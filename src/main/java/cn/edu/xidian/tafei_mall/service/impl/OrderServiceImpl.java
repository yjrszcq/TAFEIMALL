package cn.edu.xidian.tafei_mall.service.impl;

import cn.edu.xidian.tafei_mall.mapper.OrderItemMapper;
import cn.edu.xidian.tafei_mall.model.entity.*;
import cn.edu.xidian.tafei_mall.mapper.OrderMapper;
import cn.edu.xidian.tafei_mall.service.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author shenyaoguan
 * @since 2025-03-17
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderItemMapper orderItemMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;
    @Autowired
    private CartService cartService;
    @Autowired
    private CartItemService cartItemService;


    /**
     * 获取订单项
     * @param orderId 订单ID
     * @param sessionId 会话ID
     * @return 订单项列表
     */
    @Override
    public List<Order> getOrderById(String sessionId, String orderId){
        User user = userService.getUserInfo(sessionId);
        if (user == null) {
            throw new IllegalArgumentException("Invalid session ID");
        }
        if (Objects.equals(orderId, "-1") || orderId == null) {
            return orderMapper.selectList(new QueryWrapper<Order>().eq("user_id", user.getUserId()));
        } else {
            return orderMapper.selectList(new QueryWrapper<Order>().eq("order_id", orderId).eq("user_id", user.getUserId()));
        }
    }

    /**
     * 获取订单项(管理员)
     * @param orderId 订单ID
     * @return 订单项列表
     */
    public List<Order> getOrderByAdminById(String orderId){
        if (Objects.equals(orderId, "-1") || orderId == null) {
            return orderMapper.selectList(new QueryWrapper<>());
        } else {
            return orderMapper.selectList(new QueryWrapper<Order>().eq("order_id", orderId));
        }
    }

    /**
     * 创建订单
     * @param cartId 购物车ID
     * @param addressId 地址ID
     * @return 订单ID
     */
    @Override
    public String createOrder(String cartId, Order order) {
        // 获取购物车
        List<CartItem> cartItems = cartItemService.getCartItemsByCartId(cartId);
        if (cartItems.isEmpty()) {
            throw new IllegalArgumentException("Cart is empty");
        }

        // 创建订单
        order.setUserId(cartService.getCartById(cartId).getUserId());
        order.setStatus("待支付");
        order.setShippingAddressId(addressId);
        orderMapper.insert(order);
        String orderId = order.getOrderId();

        // 创建订单项
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(orderId);
            orderItem.setProductId(cartItem.getProductId());
            orderItem.setQuantity(cartItem.getQuantity());

            Optional<Product> product = productService.getProductById(cartItem.getProductId());
            if (product.isEmpty()) {
                throw new IllegalArgumentException("Invalid product ID");
            }
            BigDecimal price = product.get().getPrice();
            BigDecimal itemTotal = price.multiply(BigDecimal.valueOf(cartItem.getQuantity()));
            orderItem.setPrice(itemTotal);

            totalAmount = totalAmount.add(itemTotal);
            orderItemMapper.insert(orderItem);
        }

        // 更新订单总金额
        order.setTotalAmount(totalAmount);
        orderMapper.updateById(order);

        return orderId;
    }

    /**
     * 更新订单状态
     * @param orderId 订单ID
     * @param status 状态信息
     * @return 更新后的订单
     */
    @Override
    public Order updateOrderStatus(String orderId, Order tempOrder) {
        switch(tempOrder.getStatus()){
            case "已取消": {
                // 验证订单状态是否可以取消
                Order order = orderMapper.selectById(orderId);
                if (!order.getStatus().equals("待支付")) {
                    throw new IllegalArgumentException("Order cannot be cancelled");
                }
                // 更改订单状态为已取消
                order.setStatus("已取消");
                // 提交
                orderMapper.updateById(order);
                return order;
            }
            case "已支付": {
                // 验证订单状态是否可以支付
                Order order = orderMapper.selectById(orderId);
                if (!order.getStatus().equals("待支付")) {
                    throw new IllegalArgumentException("Order cannot be paid");
                }
                // 更改订单状态为已支付
                order.setStatus("已支付");
                // order.setPaymentMethod(tempOrder.getPaymentMethod()); // 添加支付模块后使用

                // 清空购物车
                Cart cart = cartService.getCartByUserId(order.getUserId());
                cartItemService.deleteCartItemsByCartId(cart.getCartId());
                /* // 用于可以购买购物车中的部分商品的情况
                Cart cart = cartService.getCartByUserId(order.getUserId());
                List<OrderItem> orderItems = orderItemService.getOrderItemsByOrderId(order.getOrderId());
                for (OrderItem orderItem : orderItems) {
                    cartItemService.deleteCartItem(cart.getCartId(), orderItem.getProductId());
                }
                */

                // 提交
                orderMapper.updateById(order);
                return order;
            }
            case "已发货": {
                // 验证订单状态是否可以发货
                Order order = orderMapper.selectById(orderId);
                if (!order.getStatus().equals("已支付")) {
                    throw new IllegalArgumentException("Order cannot be shipped");
                }
                // 更改订单状态为已发货
                order.setStatus("已发货");
                // 提交
                orderMapper.updateById(order);
                return order;
            }
            case "已送达": {
                // 验证订单状态是否可以送达
                Order order = orderMapper.selectById(orderId);
                if (!order.getStatus().equals("已发货")) {
                    throw new IllegalArgumentException("Order cannot be delivered");
                }
                // 更改订单状态为已送达
                order.setStatus("已送达");
                // 提交
                orderMapper.updateById(order);
                return order;
            }
            case "已完成": {
                // 验证订单状态是否可以完成
                Order order = orderMapper.selectById(orderId);
                if (!order.getStatus().equals("已发货")) {
                    throw new IllegalArgumentException("Order cannot be completed");
                }
                // 更改订单状态为已完成
                order.setStatus("已完成");
                // 提交
                orderMapper.updateById(order);
                return order;
            }
        }
        return new Order();
    }

    /**
     * 取消订单
     * @param orderId 订单ID
     * @return 是否成功
     */
    @Override
    public boolean cancelOrder(String orderId) {
        Order tempOrder = new Order();
        tempOrder.setStatus("已取消");
        Order order = updateOrderStatus(orderId, tempOrder);
        return order.getOrderId() != null;
    }
}
