package cn.edu.xidian.tafei_mall.service.impl;

import cn.edu.xidian.tafei_mall.mapper.FavoriteMapper;
import cn.edu.xidian.tafei_mall.mapper.ImageMapper;
import cn.edu.xidian.tafei_mall.mapper.ProductMapper;
import cn.edu.xidian.tafei_mall.model.entity.Favorite;
import cn.edu.xidian.tafei_mall.model.entity.Image;
import cn.edu.xidian.tafei_mall.model.entity.Product;
import cn.edu.xidian.tafei_mall.model.vo.Response.Favorite.FavoriteResponse;
import cn.edu.xidian.tafei_mall.model.vo.Response.Favorite.getFavoriteResponse;
import cn.edu.xidian.tafei_mall.service.FavoriteService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@ConditionalOnProperty(name = "features.favorite.enabled", havingValue = "true", matchIfMissing = true)
public class FavoriteServiceImpl extends ServiceImpl<FavoriteMapper, Favorite> implements FavoriteService {

    @Autowired
    private FavoriteMapper favoriteMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ImageMapper imageMapper;

    @Override
    public boolean addFavorite(String productId, String userId){
        Favorite favorite = new Favorite();
        favorite.setProductId(productId);
        favorite.setUserId(userId);
        favorite.setCreatedAt(LocalDateTime.now());
        return favoriteMapper.insert(favorite) > 0;
    }

    @Override
    public boolean removeFavorite(String productId, String userId){
        return favoriteMapper.delete(new QueryWrapper<Favorite>().eq("product_id", productId).eq("user_id", userId)) > 0;
    }

    @Override
    public getFavoriteResponse getFavorites(int page, int limit, String userId){
        if (page <= 0) {
            page = 1; // 默认从第1页开始
        }
        if (limit <= 0) {
            limit = 10; // 默认每页返回10条记录
        }
        LambdaQueryWrapper<Favorite> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Favorite::getUserId, userId); // 根据 user_id 查询

        // 分页查询
        Page<Favorite> favoritePage = new Page<>(page, limit);
        Page<Favorite> resultPage = favoriteMapper.selectPage(favoritePage, queryWrapper);

        // 获取查询结果和总数
        List<Favorite> favorites = resultPage.getRecords();

        int total = 0;
        List<FavoriteResponse> favoriteResponses = new ArrayList<>();
        for (Favorite favorite : favorites) {
            Product product = productMapper.selectById(favorite.getProductId());
            if (product != null) {
                String thumbnail = imageMapper.selectOne(new QueryWrapper<Image>().eq("product_id", product.getProductId())).getImagePath();
                favoriteResponses.add(new FavoriteResponse(product.getProductId(), product.getName(), product.getCurrentPrice(), thumbnail));
                total ++;
            }
        }
        return new getFavoriteResponse(total, favoriteResponses);
    }
}
