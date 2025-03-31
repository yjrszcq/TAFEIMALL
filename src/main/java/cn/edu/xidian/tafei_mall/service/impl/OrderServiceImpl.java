package cn.edu.xidian.tafei_mall.service.impl;

<<<<<<< HEAD
=======
import cn.edu.xidian.tafei_mall.mapper.AddressMapper;
>>>>>>> upstream/dev
import cn.edu.xidian.tafei_mall.mapper.CartItemMapper;
import cn.edu.xidian.tafei_mall.mapper.CartMapper;
import cn.edu.xidian.tafei_mall.model.entity.*;
import cn.edu.xidian.tafei_mall.mapper.OrderMapper;
import cn.edu.xidian.tafei_mall.model.vo.OrderCreateVO;
<<<<<<< HEAD
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
=======
import cn.edu.xidian.tafei_mall.model.vo.OrderUpdateVO;
import cn.edu.xidian.tafei_mall.model.vo.Response.Buyer.*;
import cn.edu.xidian.tafei_mall.model.vo.Response.Seller.updateOrderResponse;
import cn.edu.xidian.tafei_mall.service.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Contract;
import org.springframework.lang.NonNull;
>>>>>>> upstream/dev
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
<<<<<<< HEAD
    /* 由于Service层的Cart不完整，暂时用Mapper层的方法代替
=======
    /* 由于Service层的Cart和Address不完整，暂时用Mapper层的方法代替
>>>>>>> upstream/dev
    @Autowired
    private CartService cartService;
    @Autowired
    private CartItemService cartItemService;
<<<<<<< HEAD
=======
    @Autowired
    private AddressService addressService;
>>>>>>> upstream/dev
     */
    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private CartItemMapper cartItemMapper;
<<<<<<< HEAD
=======
    @Autowired
    private AddressMapper addressMapper;
>>>>>>> upstream/dev

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
<<<<<<< HEAD
    public getOrderResponse getOrderByCustomer(String userId){
        List <Order> orders = orderMapper.selectList(new QueryWrapper<Order>().eq("user_id", userId));
=======
    public getOrderBuyerResponse getOrderByCustomer(String userId){
        List <Order> orders = orderMapper.findByUserId(userId);
>>>>>>> upstream/dev
        if (orders.isEmpty()) {
            return null;
        }
        // 获取订单项
<<<<<<< HEAD
        List<OrderDetailResponse> orderDetailResponses = new ArrayList<>();
        for (Order order : orders) {
            orderDetailResponses.add(OrderDetailGenerator(order));
        }
        return new getOrderResponse(orderDetailResponses);
=======
        List<OrderBuyerResponse> orderDetailResponses = new ArrayList<>();
        for (Order order : orders) {
            orderDetailResponses.add(OrderDetailGenerator(order));
        }
        return new getOrderBuyerResponse(orderDetailResponses);
>>>>>>> upstream/dev
    }

    /**
     * 获取订单详情(买家)
     * @param OrderId 订单ID
     * @param userId 用户ID
     * @return 订单详情列表
     */
    @Override
<<<<<<< HEAD
    public getOrderResponse getOrderByCustomer(String OrderId, String userId) {
=======
    public getOrderBuyerResponse getOrderByCustomer(String OrderId, String userId) {
>>>>>>> upstream/dev
        Order order = orderMapper.selectById(OrderId);
        if (order == null) {
            return null;
        }
        if (!order.getUserId().equals(userId)) {
            throw new IllegalArgumentException("Order does not belong to current user");
        }
        // 获取订单项
<<<<<<< HEAD
        List<OrderDetailResponse> orderDetailResponses = new ArrayList<>();
        orderDetailResponses.add(OrderDetailGenerator(order));
        return new getOrderResponse(orderDetailResponses);
=======
        List<OrderBuyerResponse> orderRespons = new ArrayList<>();
        orderRespons.add(OrderDetailGenerator(order));
        return new getOrderBuyerResponse(orderRespons);
>>>>>>> upstream/dev
    }

    /**
     * 创建订单
     * @param cartId 购物车ID
     * @param orderCreateVO 地址ID
     * @param userId 用户ID
     * @return 订单ID
     */
    @Override
<<<<<<< HEAD
    public String createOrder(String cartId, OrderCreateVO orderCreateVO, String userId) {
=======
    public createOrderBuyerResponse createOrder(String cartId, OrderCreateVO orderCreateVO, String userId) {
>>>>>>> upstream/dev
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
<<<<<<< HEAD

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

=======
        // 将所有购物车项按照卖家分开，每个商家分别生成一个List<OrderItem>
        Map<String, List<OrderItem>> orderItemListMap = new HashMap<>();
        for (CartItem cartItem : cartItems) {
>>>>>>> upstream/dev
            Optional<Product> product = productService.getProductById(cartItem.getProductId());
            if (product.isEmpty()) {
                throw new IllegalArgumentException("Invalid product ID");
            }
<<<<<<< HEAD
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
=======
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
        // 获取地址
        String addressId;
        if (orderCreateVO != null) {
            addressId = orderCreateVO.getShippingAddressId();
            // Address address = addressService.getAddressById(addressId);
            Address address = addressMapper.selectById(addressId);
            if (address == null) {
                throw new IllegalArgumentException("Invalid address ID");
            }
        } else { // 如果没有传入地址ID，使用用户默认地址(默认为第一个地址)
            // Address address = addressService.getAddressByUserId(userId).get(0);
            Address address = addressMapper.selectList(new QueryWrapper<Address>().eq("user_id", userId)).get(0);
            if (address == null) {
                throw new IllegalArgumentException("Address not found");
            }
            addressId = address.getAddressId();
        }
        // 生成每个seller的订单
        // Order tempOrder = BeanUtil.toBean(orderCreateVO, Order.class);
        List<String> orderIds = new ArrayList<>();
        for (Map.Entry<String, List<OrderItem>> entry : orderItemListMap.entrySet()) {
            // 创建订单
            Order order = new Order();
            order.setUserId(userId);
            // order.setSellerId(entry.getKey());
            order.setShippingAddressId(addressId);
            order.setStatus("pending");
            order.setCreatedAt(LocalDateTime.now());
            order.setUpdatedAt(LocalDateTime.now());
            order.setOrderId(UUID.randomUUID().toString());
            orderMapper.insert(order);
            orderIds.add(order.getOrderId());
            // 生成订单项
            String orderId = order.getOrderId();
            BigDecimal totalAmount = BigDecimal.ZERO;
            for (OrderItem orderItem : entry.getValue()) {
                orderItem.setOrderId(orderId);
                orderItem.setOrderItemId(UUID.randomUUID().toString());
                orderItemService.addOrderItem(orderItem);
                totalAmount = totalAmount.add(orderItem.getPrice());
            }
            // 更新订单总金额
            order.setTotalAmount(totalAmount);
            orderMapper.updateById(order);
        }
        return new createOrderBuyerResponse(orderIds);
    }

>>>>>>> upstream/dev
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
<<<<<<< HEAD
        order.setUpdatedAt(LocalDateTime.now());
=======
>>>>>>> upstream/dev
        // 提交
        orderMapper.updateById(order);
        return true;
    }

    /**
<<<<<<< HEAD
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
=======
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
        switch (orderUpdateVO.getAction()) {
            case "ship": { // to 'shipping'
                if (!order.getStatus().equals("paid")) {
                    throw new IllegalArgumentException("Order cannot be shipping");
                }
                order.setStatus("shipping");
                // order.setTrackingNumber(orderUpdateVO.getTrackingNumber());
                order.setUpdatedAt(LocalDateTime.now());
                orderMapper.updateById(order);
                if (Objects.equals(order.getStatus(), "shipping")) {
                    return new updateOrderResponse("已发货");
                } else {
                    throw new RuntimeException("Failed to update order status");
                }

            }
            case "cancel": { // to 'canceled'
                if (!order.getStatus().equals("pending") && !order.getStatus().equals("paid")) {
                    throw new IllegalArgumentException("Order cannot be cancelled");
                }
                order.setStatus("canceled");
                order.setUpdatedAt(LocalDateTime.now());
                orderMapper.updateById(order);
                if (Objects.equals(order.getStatus(), "canceled")) {
                    return new updateOrderResponse("已取消");
                } else {
                    throw new RuntimeException("Failed to update order status");
                }
            }
            default:
                throw new IllegalArgumentException("Invalid status");
        }
>>>>>>> upstream/dev
    }

    // 生成订单详情，class内部使用
    @Contract("_ -> new")
<<<<<<< HEAD
    private @NotNull OrderDetailResponse OrderDetailGenerator(@NotNull Order order) {
        List<OrderItemResponse> orderItemResponses = new ArrayList<>();
=======
    private @NonNull OrderBuyerResponse OrderDetailGenerator(@NonNull Order order) {
        List<OrderItemBuyerResponse> orderItemBuyerResponses = new ArrayList<>();
>>>>>>> upstream/dev
        List<OrderItem> orderItems = orderItemService.getOrderItemByOrderId(order.getOrderId());
        for (OrderItem orderItem : orderItems) {
            Optional<Product> product = productService.getProductById(orderItem.getProductId());
            if (product.isEmpty()) {
                throw new IllegalArgumentException("Invalid product ID");
            }
<<<<<<< HEAD
            orderItemResponses.add(new OrderItemResponse(orderItem.getProductId(), product.get().getName(), orderItem.getQuantity(), orderItem.getPrice()));
        }
        return new OrderDetailResponse(order.getOrderId(), order.getStatus(), new getOrderItemResponse(orderItemResponses));
=======
            orderItemBuyerResponses.add(new OrderItemBuyerResponse(orderItem.getProductId(), product.get().getName(), orderItem.getQuantity(), orderItem.getPrice()));
        }
        return new OrderBuyerResponse(order.getOrderId(), order.getStatus(), new getOrderItemBuyerResponse(orderItemBuyerResponses));
>>>>>>> upstream/dev
    }

}
