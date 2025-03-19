package cn.edu.xidian.tafei_mall.controller;


import cn.edu.xidian.tafei_mall.model.vo.*;
import cn.edu.xidian.tafei_mall.service.ProductService;
import cn.edu.xidian.tafei_mall.service.impl.ProductServiceImpl;
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
    public ResponseEntity<?> addProduct(@SessionAttribute("seller") String seller, @RequestBody ProductVO productVO) {
        // Implement add product logic
        cn.edu.xidian.tafei_mall.model.entity.Product entity = new cn.edu.xidian.tafei_mall.model.entity.Product();
        entity.setName(productVO.getName());
        entity.setPrice(productVO.getPrice());
        entity.setStock(productVO.getStock());
        entity.setSeller(seller);
        ProductService productService = new ProductServiceImpl();
        productService.addProduct(productVO);
        return ResponseEntity.status(HttpStatus.CREATED).body("商品上架成功");
    }

    @PutMapping("/orders/{orderId}")
    public ResponseEntity<?> manageOrder(@PathVariable String orderId, @RequestBody Map<String, String> action) {
        // Implement manage order logic
        return ResponseEntity.ok("操作成功");
    }
}