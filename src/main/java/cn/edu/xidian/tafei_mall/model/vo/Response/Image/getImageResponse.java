package cn.edu.xidian.tafei_mall.model.vo.Response.Image;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class getImageResponse {
    private MultipartFile image;

    public getImageResponse(MultipartFile image) {
        this.image = image;
    }
}
