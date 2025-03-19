package cn.edu.xidian.tafei_mall.service;

import cn.edu.xidian.tafei_mall.model.entity.Product;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.zip.DataFormatException;

/**
 * <p>
 * 商品表 服务类
 * </p>
 *
 * @author shenyaoguan
 * @since 2025-03-17
 */
public interface ProductService extends IService<Product> {

    void addProduct(Product product) throws DataFormatException;
}
