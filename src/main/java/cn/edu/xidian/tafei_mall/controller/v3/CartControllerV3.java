package cn.edu.xidian.tafei_mall.controller.v3;

import cn.edu.xidian.tafei_mall.model.entity.User;
import cn.edu.xidian.tafei_mall.model.vo.CartItemAddVO;
import cn.edu.xidian.tafei_mall.model.vo.CartItemUpdateVO;
import cn.edu.xidian.tafei_mall.model.vo.Response.Cart.CartResponse;
import cn.edu.xidian.tafei_mall.service.CartItemService;
import cn.edu.xidian.tafei_mall.service.CartService;
import cn.edu.xidian.tafei_mall.service.RoleService;
import cn.edu.xidian.tafei_mall.service.UserService;
import cn.hutool.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;


/**
 *  <p>
 *  这里对应的是/cart路径，用于处理购物车的增删改查请求
 * </p>
 *
 * @auther: shenyaoguan
 *
 * @date: 2025-03-17
 *
 */

@RestController
@RequestMapping("/api/v3/cart")
public class CartControllerV3 {
    @Autowired
    private CartService cartService;

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @GetMapping
    public ResponseEntity<?> getCartContents(@RequestHeader("Session-Id") String sessionId) {
        try{
            User user = checkSessionId(sessionId);
            if(user == null){
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            if(roleService.verifyUserPermission(user, "cart") < 1){
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            CartResponse cart = cartService.getCart(user.getUserId());
            return new ResponseEntity<>(cart, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }


    }

    @PostMapping("/items")
    public ResponseEntity<?> addItemToCart(@RequestBody CartItemAddVO cartItemAddVO, @RequestHeader("Session-Id") String sessionId) {
        try {
            User user = checkSessionId(sessionId);
            if (user == null) {
               return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            if(roleService.verifyUserPermission(user, "cart") < 2){
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            cartService.addToCart(cartItemAddVO, user);
            return ResponseEntity.created(URI.create("/api/cart")).body(JSONUtil.createObj().set("message", "商品添加成功"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(JSONUtil.createObj().set("message", e.getMessage()));
        }
    }

    @PutMapping("/items/{itemId}")
    public ResponseEntity<?> updateCartItem(@PathVariable String itemId, @RequestBody CartItemUpdateVO cartItemUpdateVO, @RequestHeader("Session-Id") String sessionId) {
        try {
            User user = checkSessionId(sessionId);
            if (user == null) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            if(roleService.verifyUserPermission(user, "cart") < 2){
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            cartItemService.updateCartItem(itemId, cartItemUpdateVO);
            return ResponseEntity.ok(JSONUtil.createObj().set("message", "商品数量更新成功"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(JSONUtil.createObj().set("message", e.getMessage()));
        }

    }

    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<?> deleteCartItem(@PathVariable String itemId, @RequestHeader("Session-Id") String sessionId) {
        try {
            User user = checkSessionId(sessionId);
            if (user == null) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            if(roleService.verifyUserPermission(user, "cart") < 2){
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            cartItemService.deleteCartItem(itemId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.unprocessableEntity().build();
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(JSONUtil.createObj().set("message", e.getMessage()));
        }
    }

    private User checkSessionId(String sessionId) {
        if (sessionId == null) {
            throw new IllegalArgumentException("Session ID is required");
        }
        return userService.getUserInfo(sessionId);
        }
}