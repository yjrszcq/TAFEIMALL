package cn.edu.xidian.tafei_mall.controller;


import cn.edu.xidian.tafei_mall.model.entity.Order;
import cn.edu.xidian.tafei_mall.model.entity.OrderItem;
import cn.edu.xidian.tafei_mall.service.OrderItemService;
import cn.edu.xidian.tafei_mall.service.OrderService;
import io.swagger.annotations.ApiKeyAuthDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;


@ApiKeyAuthDefinition(key = "Session-ID", in = ApiKeyAuthDefinition.ApiKeyLocation.HEADER, name = "Session-ID")
@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderItemService orderItemService;
    /**
     * 获取订单详情(用户)
     * @param orderId 订单ID(不填就是查询当前用户所有订单)
     * @param sessionId Session ID
     * @return 订单详情
     */
    @GetMapping("/search")
    public ResponseEntity<?> searchOrder(@RequestHeader("Session-Id") String sessionId,
                                         @RequestParam(required = false, defaultValue = "-1") String orderId) {
        try{
            List<Order> orders = orderService.getOrderById(sessionId, orderId);
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
    /**
     * 获取订单详情(管理员)
     * @param orderId 订单ID(不填就是查询所有订单)
     * @return 订单详情
     */
    @GetMapping("/admin/search")
    public ResponseEntity<?> searchOrderByAdmin(@RequestParam(required = false, defaultValue = "-1") String orderId) {
        try{
            List<Order> orders = orderService.getOrderByAdminById(orderId);
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * 获取订单详情(用户)
     * @param orderId 订单ID(不填就是查询当前用户所有订单项)
     * @param sessionId Session ID
     * @return 订单详情
     */
    @GetMapping("/item")
    public ResponseEntity<?> getOrderItemsByOrderId(@RequestHeader("Session-Id") String sessionId,
                                                    @RequestParam(required = false, defaultValue = "-1") String orderId) {
        try{
            List<OrderItem> items = orderItemService.getOrderItemsByOrderId(sessionId, orderId);
            return ResponseEntity.ok(items);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    /**
     * 获取订单详情(管理员)
     * @param orderId 订单ID(不填就是查询所有订单项)
     * @return 订单详情
     */
    @GetMapping("/admin/item")
    public ResponseEntity<?> getOrderItemsByAdminByOrderId(@RequestParam(required = false, defaultValue = "-1") String orderId) {
        try{
            List<OrderItem> items = orderItemService.getOrderItemsByAdminByOrderId(orderId);
            return ResponseEntity.ok(items);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    /**
     * 获取订单项详情(用户)
     * @param sessionId Session ID
     * @param orderItemId 订单项ID
     * @return 订单项详情
     */
    @GetMapping("/item/id")
    public ResponseEntity<?> getOrderItemsById(@RequestHeader("Session-Id") String sessionId,
                                               @RequestParam String orderItemId) {
        try{
            OrderItem item = orderItemService.getOrderItemById(sessionId, orderItemId);
            return ResponseEntity.ok(item);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    /**
     * 获取订单项详情(管理员)
     * @param orderItemId 订单项ID
     * @return 订单项详情
     */
    @GetMapping("/admin/item/id")
    public ResponseEntity<?> getOrderItemsByAdminById(@RequestParam String orderItemId) {
        try{
            OrderItem item = orderItemService.getOrderItemByAdminById(orderItemId);
            return ResponseEntity.ok(item);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    /**
     * 创建订单
     * @param cartId 购物车ID
     * @param addressId 地址ID
     * @return 订单ID
     */
    @PostMapping("/create/{cartId}")
    public ResponseEntity<?> createOrder(@PathVariable String cartId, @RequestBody String addressId) {
        try{
            String orderId = orderService.createOrder(cartId, addressId);
            return ResponseEntity.created(URI.create("")).body(orderId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    /**
     * 更新订单状态
     * @param orderId 订单ID
     * @param status 状态信息(可附带备注，比如支付方式)
     * @return 更新后的订单
     */
    @PutMapping("/update/{orderId}")
    public ResponseEntity<?> updateOrderStatus(@PathVariable String orderId, @RequestBody Map<String, String> status) {
        try{
            Order order = orderService.updateOrderStatus(orderId, status);
            if (order == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    /**
     * 取消订单
     * @param orderId 订单ID
     * @return 是否成功
     */
    @DeleteMapping("/cancel/{orderId}")
    public ResponseEntity<?> cancelOrder(@PathVariable String orderId) {
        try{
            boolean flag = orderService.cancelOrder(orderId);
            if (!flag) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(orderId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
