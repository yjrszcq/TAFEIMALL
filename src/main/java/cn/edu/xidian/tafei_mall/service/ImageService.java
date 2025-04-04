package cn.edu.xidian.tafei_mall.service;


import cn.edu.xidian.tafei_mall.model.entity.Image;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 图片表 服务类
 * </p>
 *
 * @author shenyaoguan
 * @since 2025-03-17
 */

public interface ImageService extends IService<Image> {

    void uploadImage(String productId, MultipartFile file);
}
