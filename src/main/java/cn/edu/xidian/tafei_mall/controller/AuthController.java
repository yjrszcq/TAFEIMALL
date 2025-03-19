package cn.edu.xidian.tafei_mall.controller;

import cn.edu.xidian.tafei_mall.model.vo.*;
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

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegistration userRegistration) {
        // Implement registration logic
        return ResponseEntity.status(HttpStatus.CREATED).body("注册成功");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        // Implement login logic
        return ResponseEntity.ok("登录成功");
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        // Implement logout logic
        return ResponseEntity.ok("登出成功");
    }
}