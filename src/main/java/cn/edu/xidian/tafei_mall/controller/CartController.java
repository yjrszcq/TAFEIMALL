package cn.edu.xidian.tafei_mall.controller;

import cn.edu.xidian.tafei_mall.model.entity.Cart;
import cn.edu.xidian.tafei_mall.model.entity.CartItem;
import cn.edu.xidian.tafei_mall.model.entity.User;
import cn.edu.xidian.tafei_mall.model.vo.*;
import cn.edu.xidian.tafei_mall.service.CartItemService;
import cn.edu.xidian.tafei_mall.service.CartService;
import cn.edu.xidian.tafei_mall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


/**
 *  <p>
 *  这里对应的是/api/cart路径，用于处理购物车的增删改查请求
 * </p>
 *
 * @auther: shenyaoguan
 *
 * @date: 2025-03-17
 *
 */

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getCartContents(@RequestHeader(value = "Session-Id", required = false) String sessionId) {
        boolean isValidSession = cartService.validateSession(sessionId);
        if (!isValidSession) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "缺少Session-Id请求头"));
        }
        User user = userService.getUserInfo(sessionId);
        Cart cart = cartService.lambdaQuery()
                .eq(Cart::getUserId, user.getUserId())
                .one();
        if (cart == null) {
            return ResponseEntity.ok(Map.of("items", List.of()));
        }
        List<CartItem> cartItems = cartItemService.lambdaQuery()
                .eq(CartItem::getCartId, cart.getCartId())
                .list();
        return ResponseEntity.ok(Map.of("items", cartItems));
    }

    @PostMapping("/items")
    public ResponseEntity<?> addItemToCart(@RequestHeader(value = "Session-Id", required = true) String sessionId,
                                           @RequestBody CartItemAddVO item) {
        boolean isValidSession = cartService.validateSession(sessionId);
        if (!isValidSession) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "缺少Session-Id请求头"));
        }
        User user = userService.getUserInfo(sessionId);
        Cart cart = cartService.getOrCreateCart(user.getUserId());
        CartItem cartItem = cartService.createCartItem(cart.getCartId(), item);
        cartItemService.save(cartItem);
        return ResponseEntity.status(HttpStatus.CREATED).body("添加成功");
    }

    @PutMapping("/items/{itemId}")
    public ResponseEntity<?> updateCartItem(@PathVariable String itemId, @RequestBody Map<String, Integer> update) {
        CartItem cartItem = cartItemService.getById(itemId);
        if (cartItem == null) {
            return ResponseEntity.notFound().build();
        }
        Integer quantity = update.get("quantity");
        if (quantity != null) {
            cartItem.setQuantity(quantity);
            cartItem.setUpdatedAt(LocalDateTime.now());
            cartItemService.updateById(cartItem);
        }
        return ResponseEntity.ok("数量更新成功");
    }

    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<?> deleteCartItem(@PathVariable String itemId) {
        boolean success = cartItemService.removeById(itemId);
        if (success) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}

