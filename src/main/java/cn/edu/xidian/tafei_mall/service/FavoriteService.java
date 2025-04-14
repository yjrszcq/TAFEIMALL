package cn.edu.xidian.tafei_mall.service;

import cn.edu.xidian.tafei_mall.model.vo.Response.Favorite.getFavoriteResponse;

public interface FavoriteService {
    boolean addFavorite(String productId, String userId);
    boolean removeFavorite(String productId, String userId);
    getFavoriteResponse getFavorites(int page, int limit, String userId);
}
