package cn.edu.xidian.tafei_mall.service.impl;

import cn.edu.xidian.tafei_mall.mapper.CartItemMapper;
import cn.edu.xidian.tafei_mall.mapper.CartMapper;
import cn.edu.xidian.tafei_mall.model.entity.*;
import cn.edu.xidian.tafei_mall.mapper.OrderMapper;
import cn.edu.xidian.tafei_mall.model.vo.OrderCreateVO;
import cn.edu.xidian.tafei_mall.model.vo.OrderUpdateVO;
import cn.edu.xidian.tafei_mall.model.vo.Response.Buyer.*;
import cn.edu.xidian.tafei_mall.model.vo.Response.Seller.OrderItemResponse;
import cn.edu.xidian.tafei_mall.model.vo.Response.Seller.updateOrderResponse;
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
    public getOrderBuyerResponse getOrderByCustomer(String userId){
        List <Order> orders = orderMapper.selectList(new QueryWrapper<Order>().eq("user_id", userId));
        if (orders.isEmpty()) {
            return null;
        }
        // 获取订单项
        List<OrderBuyerResponse> orderDetailResponses = new ArrayList<>();
        for (Order order : orders) {
            orderDetailResponses.add(OrderDetailGenerator(order));
        }
        return new getOrderBuyerResponse(orderDetailResponses);
    }

    /**
     * 获取订单详情(买家)
     * @param OrderId 订单ID
     * @param userId 用户ID
     * @return 订单详情列表
     */
    @Override
    public getOrderBuyerResponse getOrderByCustomer(String OrderId, String userId) {
        Order order = orderMapper.selectById(OrderId);
        if (order == null) {
            return null;
        }
        if (!order.getUserId().equals(userId)) {
            throw new IllegalArgumentException("Order does not belong to current user");
        }
        // 获取订单项
        List<OrderBuyerResponse> orderRespons = new ArrayList<>();
        orderRespons.add(OrderDetailGenerator(order));
        return new getOrderBuyerResponse(orderRespons);
    }

    /**
     * 创建订单
     * @param cartId 购物车ID
     * @param orderCreateVO 地址ID
     * @param userId 用户ID
     * @return 订单ID
     */
    @Override
    public createOrderBuyerResponse createOrder(String cartId, OrderCreateVO orderCreateVO, String userId) {
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
        // 将所有购物车项按照卖家分开，每个商家分别生成一个List<OrderItem>
        Map<String, List<OrderItem>> orderItemListMap = new HashMap<>();
        for (CartItem cartItem : cartItems) {
            Optional<Product> product = productService.getProductById(cartItem.getProductId());
            if (product.isEmpty()) {
                throw new IllegalArgumentException("Invalid product ID");
            }
            String sellerId = product.get().getSellerId();
            if (!orderItemListMap.containsKey(sellerId)) {
                orderItemListMap.put(sellerId, new ArrayList<>());
            }
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(cartItem.getProductId());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(product.get().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
            orderItemListMap.get(sellerId).add(orderItem);
        }

        // 生成每个seller的订单
        List<String> orderIds = new ArrayList<>();
        for (Map.Entry<String, List<OrderItem>> entry : orderItemListMap.entrySet()) {
            // 创建订单
            Order order = new Order();
            order.setUserId(userId);
            // order.setSellerId(entry.getKey());
            order.setStatus("pending");
            order.setCreatedAt(LocalDateTime.now());
            order.setUpdatedAt(LocalDateTime.now());
            orderMapper.insert(order);
            orderIds.add(order.getOrderId());
            // 生成订单项
            String orderId = order.getOrderId();
            BigDecimal totalAmount = BigDecimal.ZERO;
            for (OrderItem orderItem : entry.getValue()) {
                orderItem.setOrderId(orderId);
                orderItemService.addOrderItem(orderItem);
                totalAmount = totalAmount.add(orderItem.getPrice());
            }
            // 更新订单总金额
            order.setTotalAmount(totalAmount);
            orderMapper.updateById(order);
        }
        return new createOrderBuyerResponse(orderIds);
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
     * 更新订单状态
     * @param orderId 订单ID
     * @param orderUpdateVO 订单更新信息
     * @param userId 卖家ID
     * @return 订单状态
     */
    @Override
    public updateOrderResponse updateOrderBySeller(String orderId, OrderUpdateVO orderUpdateVO, String userId){
        // 验证订单是否属于当前卖家
        Order order = orderMapper.selectById(orderId);
        if (order == null) {
            throw new RuntimeException("Order not found");
        }
        List<OrderItem> orderItems = orderItemService.getOrderItemByOrderId(orderId);
        if (orderItems.isEmpty()) {
            throw new RuntimeException("Order is empty");
        }
        Optional<Product> product = productService.getProductById(orderItems.get(0).getProductId());
        if (product.isEmpty()) {
            throw new IllegalArgumentException("Product not exist");
        }
        if (!product.get().getSellerId().equals(userId)) {
            throw new IllegalArgumentException("Order does not belong to current user");
        }
        // 更新订单状态
        Order tempOrder = BeanUtil.toBean(orderUpdateVO, Order.class);
        switch (tempOrder.getStatus()) {
            case "ship": { // to 'shipping'
                if (!order.getStatus().equals("paid")) {
                    throw new IllegalArgumentException("Order cannot be shipping");
                }
                order.setStatus("shipping");
                // order.setTrackingNumber(tempOrder.getTrackingNumber());
                order.setUpdatedAt(LocalDateTime.now());
                orderMapper.updateById(order);
                return new updateOrderResponse(order.getStatus());
            }
            case "cancel": { // to 'canceled'
                if (!order.getStatus().equals("pending") && !order.getStatus().equals("paid")) {
                    throw new IllegalArgumentException("Order cannot be cancelled");
                }
                order.setStatus("canceled");
                order.setUpdatedAt(LocalDateTime.now());
                orderMapper.updateById(order);
                return new updateOrderResponse(order.getStatus());
            }
            default:
                throw new IllegalArgumentException("Invalid status");
        }
    }

    // 生成订单详情，class内部使用
    @Contract("_ -> new")
    private @NotNull OrderBuyerResponse OrderDetailGenerator(@NotNull Order order) {
        List<OrderItemBuyerResponse> orderItemBuyerResponses = new ArrayList<>();
        List<OrderItem> orderItems = orderItemService.getOrderItemByOrderId(order.getOrderId());
        for (OrderItem orderItem : orderItems) {
            Optional<Product> product = productService.getProductById(orderItem.getProductId());
            if (product.isEmpty()) {
                throw new IllegalArgumentException("Invalid product ID");
            }
            orderItemBuyerResponses.add(new OrderItemBuyerResponse(orderItem.getProductId(), product.get().getName(), orderItem.getQuantity(), orderItem.getPrice()));
        }
        return new OrderBuyerResponse(order.getOrderId(), order.getStatus(), new getOrderItemBuyerResponse(orderItemBuyerResponses));
    }

}
