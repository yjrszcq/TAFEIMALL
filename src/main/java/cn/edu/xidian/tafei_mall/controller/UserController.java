package cn.edu.xidian.tafei_mall.controller;

import cn.edu.xidian.tafei_mall.model.vo.*;
import cn.edu.xidian.tafei_mall.model.vo.Response.Address.AddressResponse;
import cn.edu.xidian.tafei_mall.model.vo.Response.Address.getAddressResponse;
import cn.edu.xidian.tafei_mall.service.AddressService;
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

    @PutMapping("/address")
    public ResponseEntity<?> addAddress(@RequestBody AddressUpdateVO addressUpdate, @RequestHeader("Session-Id") String sessionId, @RequestParam("addressId") String addressId){
        try{
            addressService.updateAddress(addressUpdate, sessionId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e) {
            return new ResponseEntity<>(new AddressResponse("地址更新失败"), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/address")
    public ResponseEntity<?> deleteAddress(@RequestHeader("Session-Id") String sessionId, @RequestParam("addressId") Integer addressId){
        try{
            addressService.deleteAddress(addressId, sessionId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e) {
            return new ResponseEntity<>(new AddressResponse("地址删除失败"), HttpStatus.BAD_REQUEST);
        }
    }
}