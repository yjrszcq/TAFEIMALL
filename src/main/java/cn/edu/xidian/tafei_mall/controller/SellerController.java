package cn.edu.xidian.tafei_mall.controller;


import cn.edu.xidian.tafei_mall.model.entity.Product;
import cn.edu.xidian.tafei_mall.model.vo.*;
import cn.edu.xidian.tafei_mall.service.ProductService;
import cn.edu.xidian.tafei_mall.service.impl.ProductServiceImpl;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;
import java.util.zip.DataFormatException;


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
@SessionAttributes(value = "sellerid")
public class SellerController {

    @PostMapping("/products")
    public ResponseEntity<?> addProduct(@RequestBody ProductVO productVO, @ModelAttribute("sellerid") String sellerId) {
        ResponseEntity<String> response;
        //类型转换
        Product product = new Product();
        product.setName(productVO.getName());
        product.setPrice(BigDecimal.valueOf(productVO.getPrice()));
        product.setStock(productVO.getStock());
        product.setSellerId(sellerId);
        ProductService productService = new ProductServiceImpl();
        // 测试用例中没有对异常情况进行测试，这里直接返回500
        try {
            String uuid = productService.addProduct(product);
            response = ResponseEntity.status(HttpStatus.CREATED).body(uuid);
        } catch (DataFormatException e) {
            final Throwable cause = e.getCause();
            if (cause instanceof IllegalArgumentException) {
                response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(cause.getMessage());
            } else {
                response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
        return response;
    }

    @PutMapping("/orders/{orderId}")
    public ResponseEntity<?> manageOrder(@PathVariable String orderId, @RequestBody Map<String, String> action) {
        // Implement manage order logic
        return ResponseEntity.ok("操作成功");
    }
}