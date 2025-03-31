package cn.edu.xidian.tafei_mall.controller;


import cn.edu.xidian.tafei_mall.model.entity.User;
import cn.edu.xidian.tafei_mall.model.vo.OrderCreateVO;
<<<<<<< HEAD
import cn.edu.xidian.tafei_mall.model.vo.Response.Order.IdResponse;
import cn.edu.xidian.tafei_mall.model.vo.Response.Order.MessageResponse;
import cn.edu.xidian.tafei_mall.model.vo.Response.Order.getOrderResponse;
=======
import cn.edu.xidian.tafei_mall.model.vo.Response.Order.OrderIdResponse;
import cn.edu.xidian.tafei_mall.model.vo.Response.Order.MessageResponse;
import cn.edu.xidian.tafei_mall.model.vo.Response.Buyer.createOrderBuyerResponse;
import cn.edu.xidian.tafei_mall.model.vo.Response.Buyer.getOrderBuyerResponse;
>>>>>>> upstream/dev
import cn.edu.xidian.tafei_mall.service.OrderService;
import cn.edu.xidian.tafei_mall.service.UserService;
import io.swagger.annotations.ApiKeyAuthDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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
<<<<<<< HEAD
    @GetMapping("/search")
=======
    @GetMapping("")
>>>>>>> upstream/dev
    public ResponseEntity<?> searchOrder(@RequestHeader("Session-Id") String sessionId,
                                         @RequestParam(required = false, defaultValue = "-1") String orderId) {
        try{
            if (sessionId == null) {
                return new ResponseEntity<>(new MessageResponse("未登录"), HttpStatus.UNAUTHORIZED);
            }
            User user = userService.getUserInfo(sessionId);
            if (user == null) {
                return new ResponseEntity<>(new MessageResponse("用户不存在"), HttpStatus.UNAUTHORIZED);
            }
            if (orderId.equals("-1")) {
<<<<<<< HEAD
                getOrderResponse orders = orderService.getOrderByCustomer(user.getUserId());
                return new ResponseEntity<>(orders, HttpStatus.OK);
            } else {
                getOrderResponse orders = orderService.getOrderByCustomer(orderId, user.getUserId());
                return new ResponseEntity<>(orders, HttpStatus.OK);
=======
                getOrderBuyerResponse response = orderService.getOrderByCustomer(user.getUserId());
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                getOrderBuyerResponse response = orderService.getOrderByCustomer(orderId, user.getUserId());
                return new ResponseEntity<>(response, HttpStatus.OK);
>>>>>>> upstream/dev
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
    /**
     * 创建订单
     * @param cartId 购物车ID
     * @param orderCreateVO 地址ID
     * @return 订单ID
     */
    @PostMapping("/{cartId}")
<<<<<<< HEAD
    public ResponseEntity<?> createOrder(@RequestHeader("Session-Id") String sessionId, @PathVariable String cartId, @RequestBody OrderCreateVO orderCreateVO) {
=======
    public ResponseEntity<?> createOrder(@RequestHeader("Session-Id") String sessionId,
                                         @PathVariable String cartId,
                                         @RequestBody(required = false) OrderCreateVO orderCreateVO) {
>>>>>>> upstream/dev
        try{
            if (sessionId == null) {
                return new ResponseEntity<>(new MessageResponse("未登录"), HttpStatus.UNAUTHORIZED);
            }
            User user = userService.getUserInfo(sessionId);
            if (user == null) {
                return new ResponseEntity<>(new MessageResponse("用户不存在"), HttpStatus.UNAUTHORIZED);
            }
<<<<<<< HEAD
            String orderId = orderService.createOrder(cartId, orderCreateVO, user.getUserId());
            return ResponseEntity.created(URI.create("Order")).body(orderId);
=======
            createOrderBuyerResponse response = orderService.createOrder(cartId, orderCreateVO, user.getUserId());
            return new ResponseEntity<>(response, HttpStatus.OK);
>>>>>>> upstream/dev
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
    /**
     * 取消订单
     * @param orderId 订单ID
     * @return 是否成功
     */
    @DeleteMapping("/{orderId}")
<<<<<<< HEAD
    public ResponseEntity<?> cancelOrder(@RequestHeader("Session-Id") String sessionId, @PathVariable String orderId) {
=======
    public ResponseEntity<?> cancelOrder(@RequestHeader("Session-Id") String sessionId,
                                         @PathVariable String orderId) {
>>>>>>> upstream/dev
        try{
            if (sessionId == null) {
                return new ResponseEntity<>(new MessageResponse("未登录"), HttpStatus.UNAUTHORIZED);
            }
            User user = userService.getUserInfo(sessionId);
            if (user == null) {
                return new ResponseEntity<>(new MessageResponse("用户不存在"), HttpStatus.UNAUTHORIZED);
            }
            boolean flag = orderService.cancelOrder(orderId, user.getUserId());
            if (!flag) {
                return new ResponseEntity<>(new MessageResponse("取消失败"), HttpStatus.BAD_REQUEST);
            }
<<<<<<< HEAD
            return ResponseEntity.ok().body(orderId);
        } catch (Exception e) {
            return new ResponseEntity<>(new IdResponse(orderId), HttpStatus.OK);
=======
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
>>>>>>> upstream/dev
        }
    }
}