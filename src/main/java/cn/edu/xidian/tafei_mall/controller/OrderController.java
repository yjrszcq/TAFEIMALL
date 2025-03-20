package cn.edu.xidian.tafei_mall.controller;


import io.swagger.annotations.ApiKeyAuthDefinition;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;


@ApiKeyAuthDefinition(key = "Session-ID", in = ApiKeyAuthDefinition.ApiKeyLocation.HEADER, name = "Session-ID")
@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @GetMapping("/")
    public ResponseEntity<?> searchOrder() {
        return ResponseEntity.ok("");
    }

    @PostMapping("/{cartId}")
    public ResponseEntity<?> createOrder(@PathVariable String cartId) {
        String orderId = "";

        return ResponseEntity.created(URI.create(""))
                .body(orderId);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<?> cancelOrder(@PathVariable String orderId) {

        return ResponseEntity.noContent().build();
    }
}
