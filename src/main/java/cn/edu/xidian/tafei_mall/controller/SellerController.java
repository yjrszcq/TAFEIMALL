package cn.edu.xidian.tafei_mall.controller;


import cn.edu.xidian.tafei_mall.model.vo.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


/**
 *  <p>
 *  这里对应的是/api/seller路径，用于处理卖家的商品上架和订单管理请求
 * </p>
 *
 * @auther: shenyaoguan
 *
 * @date: 2025-03-17
 *
 */

@RestController
@RequestMapping("/api/seller")
public class SellerController {

    @PostMapping("/products")
    public ResponseEntity<?> addProduct(@RequestBody Product product) {
        // Implement add product logic
        return ResponseEntity.status(HttpStatus.CREATED).body("商品上架成功");
    }

    @PutMapping("/orders/{orderId}")
    public ResponseEntity<?> manageOrder(@PathVariable String orderId, @RequestBody Map<String, String> action) {
        // Implement manage order logic
        return ResponseEntity.ok("操作成功");
    }
}