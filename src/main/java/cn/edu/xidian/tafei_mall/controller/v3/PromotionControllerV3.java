package cn.edu.xidian.tafei_mall.controller.v3;


import cn.edu.xidian.tafei_mall.model.vo.Response.Order.MessageResponse;
import cn.edu.xidian.tafei_mall.model.vo.Response.Promotion.PromotionListResponse;
import cn.edu.xidian.tafei_mall.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



/**
 *  <p>
 *  /promotion路径
 * </p>
 *
 * @auther: diol-x
 *
 * @date: 2025-04-17
 *
 */
@RestController
@RequestMapping("/api/v3/promotions")
public class PromotionControllerV3 {
    @Autowired
    private PromotionService promotionService;

    @GetMapping("/active")
    public ResponseEntity<?> getActivePromotions(
                                        @RequestParam(defaultValue = "1") int page,
                                        @RequestParam(defaultValue = "10") int limit) {
        try {
            PromotionListResponse response = promotionService.getActivePromotions(page,limit);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
