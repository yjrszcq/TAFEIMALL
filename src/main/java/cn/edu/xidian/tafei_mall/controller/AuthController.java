package cn.edu.xidian.tafei_mall.controller;

import cn.edu.xidian.tafei_mall.model.vo.Response.Auth.RegisterResponse;
import cn.edu.xidian.tafei_mall.model.entity.User;
import cn.edu.xidian.tafei_mall.model.vo.*;
import cn.edu.xidian.tafei_mall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
*  <p>
*  这里对应的是/auth路径，用于处理用户的注册、登录、登出请求
* </p>
*
* @auther: shenyaoguan
*
* @date: 2025-03-17
*
*/

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegistrationVO userRegistrationVO) {
        User user = userService.register(userRegistrationVO);
        return new ResponseEntity<>(new RegisterResponse(user.getUserId(),"注册成功"),HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestVO loginRequestVO) {
        // Implement login logic
        return ResponseEntity.ok("登录成功");
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        // Implement logout logic
        return ResponseEntity.ok("登出成功");
    }
}