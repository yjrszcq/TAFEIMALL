package cn.edu.xidian.tafei_mall.service.impl;

import cn.edu.xidian.tafei_mall.mapper.OrderMapper;
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
    private OrderMapper orderMapper;
    private OrderItemMapper orderItemMapper;

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
        Order order = orderMapper.selectList(new QueryWrapper<Order>().eq("order_id",  orderItem.getOrderId()).eq("user_id", user.getUserId())).get(0);
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