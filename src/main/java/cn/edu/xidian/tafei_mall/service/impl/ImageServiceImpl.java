package cn.edu.xidian.tafei_mall.service.impl;

import cn.edu.xidian.tafei_mall.mapper.ImageMapper;
import cn.edu.xidian.tafei_mall.model.entity.Image;
import cn.edu.xidian.tafei_mall.service.ImageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URI;
import java.util.UUID;

@Service
public class ImageServiceImpl extends ServiceImpl<ImageMapper, Image> implements ImageService {

    @Value("${web.upload-path}")
    private String uploadPath;


    @Override
    public URI uploadImage(String productId, MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        String saveUri = uploadPath + "/" + productId + "/" + fileName;
        File uploadFile = new File(saveUri);
        if (!uploadFile.exists()) {
            uploadFile.mkdirs();
        }
        file.transferTo(uploadFile);


        Image image = new Image();
        image.setProductId(productId);
        image.setImagePath(productId+ "/" + file.getOriginalFilename());
//        image.setImageId(UUID.randomUUID().toString());
        saveOrUpdate(image);
        return URI.create(productId + "/" + fileName);
    }

    @Override
    public InputStream getImage(String imagePath) {
        File file = new File(uploadPath + "/" + imagePath);
        if (file.exists()) {
            try {
                return new FileInputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
