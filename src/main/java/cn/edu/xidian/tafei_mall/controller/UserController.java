package cn.edu.xidian.tafei_mall.controller;

import cn.edu.xidian.tafei_mall.model.vo.*;
import cn.edu.xidian.tafei_mall.model.vo.Response.Address.AddressResponse;
import cn.edu.xidian.tafei_mall.service.AddressService;
import cn.edu.xidian.tafei_mall.service.UserService;
import io.swagger.annotations.ResponseHeader;
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

    @PutMapping("/address")
    public ResponseEntity<?> updateAddress(@RequestBody AddressUpdateVO addressUpdate, @RequestHeader("Session-Id") String sessionId){
        try{
            addressService.updateAddress(addressUpdate, sessionId);
            return new ResponseEntity<>(new AddressResponse("地址更新成功"), HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(new AddressResponse("地址更新失败"), HttpStatus.BAD_REQUEST);
        }
    }
}