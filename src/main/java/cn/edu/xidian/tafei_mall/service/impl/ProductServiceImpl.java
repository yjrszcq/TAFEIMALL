package cn.edu.xidian.tafei_mall.service.impl;

import cn.edu.xidian.tafei_mall.model.entity.Product;
import cn.edu.xidian.tafei_mall.mapper.ProductMapper;
import cn.edu.xidian.tafei_mall.service.ProductService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.zip.DataFormatException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * <p>
 * 商品表 服务实现类
 * </p>
 *
 * @author shenyaoguan
 *
 * @since 2025-03-17
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {
    @Override
    public void addProduct(Product product) throws DataFormatException {

    }

    @Autowired
    private ProductMapper productMapper;
    @Override
    public Map<String, Object> searchProducts(String keyword, int page, int limit) {
        LambdaQueryWrapper<Product> queryWrapper = new LambdaQueryWrapper<>();

        // 如果 keyword 不是空，则执行模糊查询
        if (keyword != null && !keyword.trim().isEmpty()) {
            queryWrapper.like(Product::getName, keyword);
        }

        // 分页查询
        Page<Product> productPage = new Page<>(page, limit);
        Page<Product> resultPage = productMapper.selectPage(productPage, queryWrapper);

        // 构造返回数据
        List<Product> products = resultPage.getRecords();
        long total = resultPage.getTotal();

        Map<String, Object> response = new HashMap<>();
        response.put("total", total);
        response.put("results", products);
        return response;
    }

    /**
     * 根据 ID 获取商品详情
     * @param productId 商品ID
     * @return 商品信息
     */
    @Override
    public Optional<Product> getProductById(String productId) {
        return Optional.ofNullable(productMapper.selectById(productId));
    }

}
