package cn.edu.xidian.tafei_mall.service.impl;

import cn.edu.xidian.tafei_mall.model.entity.Order;
import cn.edu.xidian.tafei_mall.model.entity.OrderItem;
import cn.edu.xidian.tafei_mall.mapper.OrderItemMapper;
import cn.edu.xidian.tafei_mall.model.entity.User;
import cn.edu.xidian.tafei_mall.service.OrderItemService;
import cn.edu.xidian.tafei_mall.service.OrderService;
import cn.edu.xidian.tafei_mall.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 订单项表 服务实现类
 * </p>
 *
 * @author shenyaoguan
 * @since 2025-03-17
 */
@Service
public class OrderItemServiceImpl extends ServiceImpl<OrderItemMapper, OrderItem> implements OrderItemService {
    @Autowired
    private UserService userService;
    private OrderService orderService;
    private OrderItemMapper orderItemMapper;

    @Override
    public List<OrderItem> getOrderItemsByOrderId(String sessionId, String orderId){
        // 验证用户是否登录
        User user = userService.getUserInfo(sessionId);
        if (user == null) {
            throw new IllegalArgumentException("Invalid session ID");
        }
        // 获取订单项
        if (Objects.equals(orderId, "-1") || orderId == null) { // 返回当前用户全部订单项
            // 获取当前用户的所有订单
            List<Order> orders = orderService.getOrderById(sessionId, "-1");
            if (orders == null) {
                throw new IllegalArgumentException("No orders found");
            }
            // 获取所有订单项
            List<OrderItem> orderItems = new ArrayList<>();
            for (Order order : orders) {
                orderItems.addAll(orderItemMapper.selectList(new QueryWrapper<OrderItem>().eq("order_id", order.getOrderId())));
            }
            return orderItems;
        } else {// 返回当前用户当前订单的订单项
            List<Order> order = orderService.getOrderById(sessionId, orderId);
            // 验证订单是否存在
            if (order == null) {
                throw new IllegalArgumentException("Invalid order ID");
            }
            return orderItemMapper.selectList(new QueryWrapper<OrderItem>().eq("order_id", orderId));
        }
    }

    @Override
    public List<OrderItem> getOrderItemsByAdminByOrderId(String orderId){
        if (Objects.equals(orderId, "-1") || orderId == null) { // 返回全部订单项
            // 获取所有订单
            List<Order> orders = orderService.getOrderByAdminById("-1");
            if (orders == null) {
                throw new IllegalArgumentException("No orders found");
            }
            // 获取所有订单项
            List<OrderItem> orderItems = new ArrayList<>();
            for (Order order : orders) {
                orderItems.addAll(orderItemMapper.selectList(new QueryWrapper<OrderItem>().eq("order_id", order.getOrderId())));
            }
            return orderItems;
        } else {// 返回指定订单的订单项
            List<Order> order = orderService.getOrderByAdminById(orderId);
            // 验证订单是否存在
            if (order == null) {
                throw new IllegalArgumentException("Invalid order ID");
            }
            return orderItemMapper.selectList(new QueryWrapper<OrderItem>().eq("order_id", orderId));
        }
    }

    @Override
    public OrderItem getOrderItemById(String sessionId, String orderItemId){
        // 验证用户是否登录
        User user = userService.getUserInfo(sessionId);
        if (user == null) {
            throw new IllegalArgumentException("Invalid session ID");
        }
        // 获取订单项
        OrderItem orderItem = orderItemMapper.selectOne(new QueryWrapper<OrderItem>().eq("order_item_id", orderItemId));
        // 验证订单项是否存在
        if (orderItem == null) {
            throw new IllegalArgumentException("Invalid order item ID");
        }
        // 验证订单是否属于当前用户
        Order order = orderService.getOrderById(sessionId, orderItem.getOrderId()).get(0);
        if (order == null) {
            throw new IllegalArgumentException("Order does not belong to current user");
        }
        return orderItem;
    }
    public OrderItem getOrderItemByAdminById(String orderItemId){
        // 获取订单项
        OrderItem orderItem = orderItemMapper.selectOne(new QueryWrapper<OrderItem>().eq("order_item_id", orderItemId));
        // 验证订单项是否存在
        if (orderItem == null) {
            throw new IllegalArgumentException("Invalid order item ID");
        }
        return orderItem;
    }
}
