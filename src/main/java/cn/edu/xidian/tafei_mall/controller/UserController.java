package cn.edu.xidian.tafei_mall.controller;

import cn.edu.xidian.tafei_mall.model.vo.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @PutMapping("/address")
    public ResponseEntity<?> updateAddress(@RequestBody AddressUpdate addressUpdate) {
        // Implement address update logic
        return ResponseEntity.ok("地址更新成功");
    }
}