package cn.edu.xidian.tafei_mall.controller.v3;

import cn.edu.xidian.tafei_mall.model.entity.User;
import cn.edu.xidian.tafei_mall.model.vo.Response.Image.ImageUploadResponse;
import cn.edu.xidian.tafei_mall.model.vo.Response.Order.MessageResponse;
import cn.edu.xidian.tafei_mall.service.ImageService;
import cn.edu.xidian.tafei_mall.service.ProductService;
import cn.edu.xidian.tafei_mall.service.RoleService;
import cn.edu.xidian.tafei_mall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
@RequestMapping("/api/v3/image")
public class ImageControllerV3 {

    @Autowired
    private UserService userService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private RoleService roleService;

    @PostMapping("{productId}")
    public ResponseEntity<?> imageUploadToLsky(@PathVariable("productId") String productId, @RequestParam("image") MultipartFile image,
                                               @RequestHeader("Session-Id") String sessionId) {
        try {
            if (sessionId == null) {
                return new ResponseEntity<>(new MessageResponse("未登录"), HttpStatus.UNAUTHORIZED);
            }
            User user = userService.getUserInfo(sessionId);
            if (user == null) {
                return new ResponseEntity<>(new MessageResponse("用户不存在"), HttpStatus.UNAUTHORIZED);
            }
            if(!roleService.verifyUserPermission(user, "product:edit")){
                return new ResponseEntity<>(new MessageResponse("无权限"), HttpStatus.FORBIDDEN);
            }
            ImageUploadResponse response = imageService.uploadToLsky(productId, image);
            if (response != null) {
                return ResponseEntity.ok().body(response);
            } else {
                return new ResponseEntity<>(new MessageResponse("上传失败"), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
