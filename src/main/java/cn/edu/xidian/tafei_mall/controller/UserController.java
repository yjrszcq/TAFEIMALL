package cn.edu.xidian.tafei_mall.controller;

import cn.edu.xidian.tafei_mall.model.vo.*;
import cn.edu.xidian.tafei_mall.service.UserService;
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

    @PutMapping("/address")
    public ResponseEntity<?> updateAddress(@RequestBody AddressUpdateVO addressUpdate) {
        // Implement address update logic
        return ResponseEntity.ok("地址更新成功");
    }
}