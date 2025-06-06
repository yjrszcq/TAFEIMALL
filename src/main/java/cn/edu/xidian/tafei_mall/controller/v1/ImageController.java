package cn.edu.xidian.tafei_mall.controller.v1;

import cn.edu.xidian.tafei_mall.model.vo.ImageVO;
import cn.edu.xidian.tafei_mall.service.ImageService;
import cn.edu.xidian.tafei_mall.service.ProductService;
import cn.edu.xidian.tafei_mall.service.UserService;
import cn.hutool.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

/**
 * <p>
 *     ImageController
 * </p>
 *
 *
 * 这个类是一个控制器，用于处理与图像相关的请求。
 *
 * @author shenyaoguan
 *
 * @since 2025-03-17
 */


@RestController
@RequestMapping("/api/v1/image")
public class ImageController {
    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Autowired
    private ImageService imageService;

    @PostMapping("{productId}")
    public ResponseEntity<?> createImage(@PathVariable("productId") String productId, @RequestParam("image") MultipartFile image,
                                         @RequestHeader("Session-Id") String sessionId) {
        // 检查是否登录
        if (sessionId == null) {
            return ResponseEntity.status(401).build();
        }
        // 检查用户是否存在
        if (userService.getUserInfo(sessionId) == null) {
            return ResponseEntity.status(401).build();
        }
        // 检查产品是否存在
        if (productService.getProductById(productId).isEmpty()) {
            return ResponseEntity.status(404).body("Product not found");
        }
        try {
            // 这里可以添加处理图像上传的逻辑
            URI imagePath = imageService.uploadToLocal(productId, image);
            return ResponseEntity.created(imagePath).body(JSONUtil.createObj().set("imagePath", imagePath));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
}
