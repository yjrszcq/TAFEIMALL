package cn.edu.xidian.tafei_mall.controller;


import cn.edu.xidian.tafei_mall.model.entity.User;
import cn.edu.xidian.tafei_mall.model.vo.OrderCreateVO;
import cn.edu.xidian.tafei_mall.model.vo.Response.Order.getOrderResponse;
import cn.edu.xidian.tafei_mall.service.OrderService;
import cn.edu.xidian.tafei_mall.service.UserService;
import io.swagger.annotations.ApiKeyAuthDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;


@ApiKeyAuthDefinition(key = "Session-ID", in = ApiKeyAuthDefinition.ApiKeyLocation.HEADER, name = "Session-ID")
@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;
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
            if (sessionId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            User user = userService.getUserInfo(sessionId);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            if (orderId.equals("-1")) {
                getOrderResponse orders = orderService.getOrderByCustomer(user.getUserId());
                return ResponseEntity.ok().body(orders);
            } else {
                getOrderResponse orders = orderService.getOrderByCustomer(orderId, user.getUserId());
                return ResponseEntity.ok().body(orders);
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    /**
     * 创建订单
     * @param cartId 购物车ID
     * @param orderCreateVO 地址ID
     * @return 订单ID
     */
    @PostMapping("/{cartId}")
    public ResponseEntity<?> createOrder(@RequestHeader("Session-Id") String sessionId, @PathVariable String cartId, @RequestBody OrderCreateVO orderCreateVO) {
        try{
            if (sessionId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            User user = userService.getUserInfo(sessionId);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            String orderId = orderService.createOrder(cartId, orderCreateVO, user.getUserId());
            return ResponseEntity.created(URI.create("Order")).body(orderId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    /**
     * 取消订单
     * @param orderId 订单ID
     * @return 是否成功
     */
    @DeleteMapping("/{orderId}")
    public ResponseEntity<?> cancelOrder(@RequestHeader("Session-Id") String sessionId, @PathVariable String orderId) {
        try{
            if (sessionId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            User user = userService.getUserInfo(sessionId);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            boolean flag = orderService.cancelOrder(orderId, user.getUserId());
            if (!flag) {
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.ok().body(orderId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}