package cn.edu.xidian.tafei_mall.service.impl;

import cn.edu.xidian.tafei_mall.mapper.OrderItemMapper;
import cn.edu.xidian.tafei_mall.model.entity.*;
import cn.edu.xidian.tafei_mall.mapper.OrderMapper;
import cn.edu.xidian.tafei_mall.model.vo.OrderCreateVO;
import cn.edu.xidian.tafei_mall.model.vo.Response.Order.OrderDetailResponse;
import cn.edu.xidian.tafei_mall.model.vo.Response.Order.getOrderItemResponse;
import cn.edu.xidian.tafei_mall.model.vo.Response.Order.getOrderRespnose;
import cn.edu.xidian.tafei_mall.service.*;
import cn.hutool.core.bean.BeanUtil;
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
    private ProductService productService;
    @Autowired
    private CartService cartService;
    @Autowired
    private CartItemService cartItemService;
    @Autowired
    private OrderItemService orderItemService;

    /**
     * 获取订单项
     * @param orderId 订单ID
     * @param userId 用户ID
     * @return 订单项列表
     */
    @Override
    public getOrderRespnose getOrderById(String orderId, String userId){
        if (Objects.equals(orderId, "-1") || orderId == null) { // 获取所有订单
            List <Order> orders = orderMapper.selectList(new QueryWrapper<Order>().eq("user_id", userId));
            if (orders.isEmpty()) {
                return new getOrderRespnose(new ArrayList<>());
            }
            // 获取订单项
            List<OrderDetailResponse> orderDetailResponses = new ArrayList<>();
            for (Order order : orders) {
                List<OrderItem> orderItems = orderItemService.getOrderItemByOrderId(orderId);
                orderDetailResponses.add(new OrderDetailResponse(order.getOrderId(), order.getUserId(), order.getTotalAmount(), order.getPaymentMethod(), order.getShippingAddressId(), order.getStatus(), new getOrderItemResponse(orderItems)));
            }
            return new getOrderRespnose(orderDetailResponses);
        } else { // 获取指定订单
            Order order = orderMapper.selectOne(new QueryWrapper<Order>().eq("order_id", orderId));
            if (order == null) {
                return new getOrderRespnose(new ArrayList<>());
            }
            if (!order.getUserId().equals(userId)) {
                throw new IllegalArgumentException("Order does not belong to current user");
            }
            // 获取订单项
            List<OrderDetailResponse> orderDetailResponses = new ArrayList<>();
            List<OrderItem> orderItems = orderItemService.getOrderItemByOrderId(orderId);
            orderDetailResponses.add(new OrderDetailResponse(order.getOrderId(), order.getUserId(), order.getTotalAmount(), order.getPaymentMethod(), order.getShippingAddressId(), order.getStatus(), new getOrderItemResponse(orderItems)));
            return new getOrderRespnose(orderDetailResponses);
        }
    }

    /**
     * 创建订单
     * @param cartId 购物车ID
     * @param orderCreateVO 地址ID
     * @param userId 用户ID
     * @return 订单ID
     */
    @Override
    public String createOrder(String cartId, OrderCreateVO orderCreateVO, String userId) {
        Cart cart = cartService.getCartById(cartId);
        if (cart == null) {
            throw new IllegalArgumentException("Invalid cart ID");
        }
        if (!cart.getUserId().equals(userId)) {
            throw new IllegalArgumentException("Cart does not belong to current user");
        }
        // 获取购物车
        List<CartItem> cartItems = cartItemService.getCartItemsByCartId(cartId);
        if (cartItems.isEmpty()) {
            throw new IllegalArgumentException("Cart is empty");
        }

        // 创建订单
        Order order = BeanUtil.toBean(orderCreateVO, Order.class);
        order.setUserId(userId);
        order.setStatus("待支付");
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
     * 取消订单
     * @param orderId 订单ID
     * @return 是否成功
     */
    @Override
    public boolean cancelOrder(String orderId, String userId) {
        // 验证订单状态是否可以取消
        Order order = orderMapper.selectById(orderId);
        if (order == null) {
            throw new IllegalArgumentException("Invalid order ID");
        }
        if (!order.getUserId().equals(userId)) {
            throw new IllegalArgumentException("Order does not belong to current user");
        }
        if (!order.getStatus().equals("待支付")) {
            throw new IllegalArgumentException("Order cannot be cancelled");
        }
        // 更改订单状态为已取消
        order.setStatus("已取消");
        // 提交
        orderMapper.updateById(order);
        return true;
    }

    /**
     * 获取订单项(卖家)
     * @param userId 卖家ID
     * @return 订单项列表
     */
    public getOrderRespnose getOrderBySeller(String userId){
        /* 卖家视角下的订单列表，由于数据库不完整，暂时无法实现
        List <Order> orders = orderMapper.selectList(new LambdaQueryWrapper<Order>().eq(Order::getSellerId, userId));
        if (orders.isEmpty()) {
            return new getOrderRespnose(new ArrayList<>());
        }
        List<OrderDetailResponse> orderDetailResponses = new ArrayList<>();
        for (Order order : orders) {
            List<OrderItem> orderItems = orderItemMapper.selectList(new QueryWrapper<OrderItem>().eq("order_id", order.getOrderId()));
            orderDetailResponses.add(new OrderDetailResponse(order.getOrderId(), order.getUserId(), order.getTotalAmount(), order.getPaymentMethod(), order.getShippingAddressId(), order.getStatus(), new getOrderItemResponse(orderItems)));
        }
        return new getOrderRespnose(orderDetailResponses);
        */
    }
}