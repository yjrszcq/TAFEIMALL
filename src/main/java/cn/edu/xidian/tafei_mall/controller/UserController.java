package cn.edu.xidian.tafei_mall.controller;

import cn.edu.xidian.tafei_mall.model.entity.User;
import cn.edu.xidian.tafei_mall.model.vo.*;
import cn.edu.xidian.tafei_mall.model.vo.Response.Address.AddressResponse;
import cn.edu.xidian.tafei_mall.model.vo.Response.Address.getAddressResponse;
import cn.edu.xidian.tafei_mall.model.vo.Response.Favorite.getFavoriteResponse;
import cn.edu.xidian.tafei_mall.model.vo.Response.Order.MessageResponse;
import cn.edu.xidian.tafei_mall.service.AddressService;
import cn.edu.xidian.tafei_mall.service.FavoriteService;
import cn.edu.xidian.tafei_mall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 *  <p>
 *  这里对应的是/api/users路径，用于处理用户的地址更新请求
 * </p>
 *
 * @auther: shenyaoguan
 *
 * @date: 2025-03-17
 *
 */



@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private AddressService addressService;
    @Autowired(required = false)
    private FavoriteService favoriteService;
    @Autowired
    private UserService userService;

    @PostMapping("/address")
    public ResponseEntity<?> addAddress(@RequestBody AddressUpdateVO addressUpdate, @RequestHeader("Session-Id") String sessionId){
        try{
            addressService.addAddress(addressUpdate, sessionId);
            return new ResponseEntity<>(new AddressResponse("地址添加成功"), HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(new AddressResponse("地址添加失败"), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/address")
    public ResponseEntity<?> getAddress(@RequestHeader("Session-Id") String sessionId){
        try {
            getAddressResponse res = addressService.getAddress(sessionId);
            return new ResponseEntity<>(res, HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(new AddressResponse("获取地址失败"), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/address/{addressId}")
    public ResponseEntity<?> addAddress(@RequestBody AddressUpdateVO addressUpdate, @RequestHeader("Session-Id") String sessionId, @PathVariable String addressId){
        try{
            addressService.updateAddress(addressUpdate, sessionId, addressId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e) {
            return new ResponseEntity<>(new AddressResponse("地址更新失败"), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/address/{addressId}")
    public ResponseEntity<?> deleteAddress(@RequestHeader("Session-Id") String sessionId, @PathVariable String addressId){
        try{
            addressService.deleteAddress(addressId, sessionId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e) {
            return new ResponseEntity<>(new AddressResponse("地址删除失败"), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/favorites")
    public ResponseEntity<?> getFavorites(@RequestParam(defaultValue = "1") int page,
                                          @RequestParam(defaultValue = "10") int limit,
                                          @RequestHeader("Session-Id") String sessionId){
        try{

            if (sessionId == null) {
                return new ResponseEntity<>(new MessageResponse("未登录"), HttpStatus.UNAUTHORIZED);
            }
            User user = userService.getUserInfo(sessionId);
            if (user == null) {
                return new ResponseEntity<>(new MessageResponse("用户不存在"), HttpStatus.UNAUTHORIZED);
            }
            getFavoriteResponse res = favoriteService.getFavorites(page, limit, user.getUserId());
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/favorites/{productId}")
    public ResponseEntity<?> deleteFavorite(@RequestHeader("Session-Id") String sessionId, @PathVariable String productId){
        try{

            if (favoriteService == null) {
                return new ResponseEntity<>(new MessageResponse("收藏功能未启用"),HttpStatus.UNAUTHORIZED);
            }
            if (sessionId == null) {
                return new ResponseEntity<>(new MessageResponse("未登录"), HttpStatus.UNAUTHORIZED);
            }
            User user = userService.getUserInfo(sessionId);
            if (user == null) {
                return new ResponseEntity<>(new MessageResponse("用户不存在"), HttpStatus.UNAUTHORIZED);
            }
            boolean flag = favoriteService.removeFavorite(productId, user.getUserId());
            if (!flag) {
                return new ResponseEntity<>(new MessageResponse("移除失败"), HttpStatus.BAD_REQUEST);
            }
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/favorites/{productId}")
    public ResponseEntity<?> addFavorite(@RequestHeader("Session-Id") String sessionId, @PathVariable String productId){
        try{

            if (favoriteService == null) {
                return new ResponseEntity<>(new MessageResponse("收藏功能未启用"),HttpStatus.UNAUTHORIZED);
            }
            if (sessionId == null) {
                return new ResponseEntity<>(new MessageResponse("未登录"), HttpStatus.UNAUTHORIZED);
            }
            User user = userService.getUserInfo(sessionId);
            if (user == null) {
                return new ResponseEntity<>(new MessageResponse("用户不存在"), HttpStatus.UNAUTHORIZED);
            }
            boolean flag = favoriteService.addFavorite(productId, user.getUserId());
            if (!flag) {
                return new ResponseEntity<>(new MessageResponse("收藏失败"), HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(new MessageResponse("收藏成功"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}