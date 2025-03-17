package cn.edu.xidian.tafei_mall.controller;

import cn.edu.xidian.tafei_mall.model.vo.*;
import org.springframework.web.bind.annotation.*;


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