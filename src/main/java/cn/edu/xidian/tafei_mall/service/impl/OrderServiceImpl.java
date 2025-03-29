package cn.edu.xidian.tafei_mall.service.impl;

import cn.edu.xidian.tafei_mall.mapper.CartItemMapper;
import cn.edu.xidian.tafei_mall.mapper.CartMapper;
import cn.edu.xidian.tafei_mall.model.entity.*;
import cn.edu.xidian.tafei_mall.mapper.OrderMapper;
import cn.edu.xidian.tafei_mall.model.vo.OrderCreateVO;
import cn.edu.xidian.tafei_mall.model.vo.Response.Order.OrderDetailResponse;
import cn.edu.xidian.tafei_mall.model.vo.Response.Order.OrderItemResponse;
import cn.edu.xidian.tafei_mall.model.vo.Response.Order.getOrderItemResponse;
import cn.edu.xidian.tafei_mall.model.vo.Response.Order.getOrderResponse;
import cn.edu.xidian.tafei_mall.service.*;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
    private OrderItemService orderItemService;
    @Autowired
    private ProductService productService;
    /* 由于Service层的Cart不完整，暂时用Mapper层的方法代替
    @Autowired
    private CartService cartService;
    @Autowired
    private CartItemService cartItemService;
     */
    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private CartItemMapper cartItemMapper;

    /**
     * 获取订单(内部使用)
     * @param orderId 订单ID
     * @return 订单项列表
     */
    @Override
    public Order getOrderById(String orderId){
        return orderMapper.selectById(orderId);
    }

    /**
     * 获取订单详情(买家)
     * @param userId 用户ID
     * @return 订单详情列表
     */
    @Override
    public getOrderResponse getOrderByCustomer(String userId){
        List <Order> orders = orderMapper.selectList(new QueryWrapper<Order>().eq("user_id", userId));
        if (orders.isEmpty()) {
            return null;
        }
        // 获取订单项
        List<OrderDetailResponse> orderDetailResponses = new ArrayList<>();
        for (Order order : orders) {
            orderDetailResponses.add(OrderDetailGenerator(order));
        }
        return new getOrderResponse(orderDetailResponses);
    }

    /**
     * 获取订单详情(买家)
     * @param OrderId 订单ID
     * @param userId 用户ID
     * @return 订单详情列表
     */
    @Override
    public getOrderResponse getOrderByCustomer(String OrderId, String userId) {
        Order order = orderMapper.selectById(OrderId);
        if (order == null) {
            return null;
        }
        if (!order.getUserId().equals(userId)) {
            throw new IllegalArgumentException("Order does not belong to current user");
        }
        // 获取订单项
        List<OrderDetailResponse> orderDetailResponses = new ArrayList<>();
        orderDetailResponses.add(OrderDetailGenerator(order));
        return new getOrderResponse(orderDetailResponses);
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
        // Cart cart = cartService.getCartById(cartId);
        Cart cart = cartMapper.selectById(cartId);
        if (cart == null) {
            throw new IllegalArgumentException("Invalid cart ID");
        }
        if (!cart.getUserId().equals(userId)) {
            throw new IllegalArgumentException("Cart does not belong to current user");
        }
        // 获取购物车
        // List<CartItem> cartItems = cartItemService.getCartItemsByCartId(cartId);
        List<CartItem> cartItems = cartItemMapper.selectList(new QueryWrapper<CartItem>().eq("cart_id", cartId));
        if (cartItems.isEmpty()) {
            throw new IllegalArgumentException("Cart is empty");
        }

        // 创建订单
        Order order = BeanUtil.toBean(orderCreateVO, Order.class);
        order.setUserId(userId);
        order.setStatus("pending"); // 已提交未付款
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
            orderItemService.addOrderItem(orderItem);
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
        Order order = getOrderById(orderId);
        if (order == null) {
            throw new RuntimeException("Order not found");
        }
        if (!order.getUserId().equals(userId)) {
            throw new IllegalArgumentException("Order does not belong to current user");
        }
        if (!order.getStatus().equals("pending") && !order.getStatus().equals("paid")) {
            throw new IllegalArgumentException("Order cannot be cancelled");
        }
        // 更改订单状态为已取消
        order.setStatus("canceled");
        order.setUpdatedAt(LocalDateTime.now());
        // 提交
        orderMapper.updateById(order);
        return true;
    }

    /**
     * 获取订单项(卖家)
     * @param userId 卖家ID
     * @return 订单项列表
     */
    @Override
    public getOrderResponse getOrderBySeller(String userId){
        /* 卖家视角下的订单列表，由于数据库不完整，暂时无法实现
        List <Order> orders = orderMapper.selectList(new LambdaQueryWrapper<Order>().eq(Order::getSellerId, userId));
        if (orders.isEmpty()) {
            return new null;
        }
        List<OrderDetailResponse> orderDetailResponses = new ArrayList<>();
        for (Order order : orders) {
            orderDetailResponses.add(OrderDetailGenerator(order));
        }
        return new getOrderResponse(orderDetailResponses);
        */
        return null;
    }

    // 生成订单详情，class内部使用
    @Contract("_ -> new")
    private @NotNull OrderDetailResponse OrderDetailGenerator(@NotNull Order order) {
        List<OrderItemResponse> orderItemResponses = new ArrayList<>();
        List<OrderItem> orderItems = orderItemService.getOrderItemByOrderId(order.getOrderId());
        for (OrderItem orderItem : orderItems) {
            Optional<Product> product = productService.getProductById(orderItem.getProductId());
            if (product.isEmpty()) {
                throw new IllegalArgumentException("Invalid product ID");
            }
            orderItemResponses.add(new OrderItemResponse(orderItem.getProductId(), product.get().getName(), orderItem.getQuantity(), orderItem.getPrice()));
        }
        return new OrderDetailResponse(order.getOrderId(), order.getStatus(), new getOrderItemResponse(orderItemResponses));
    }

}
