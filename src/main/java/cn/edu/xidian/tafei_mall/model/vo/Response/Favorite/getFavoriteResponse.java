package cn.edu.xidian.tafei_mall.model.vo.Response.Favorite;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class getFavoriteResponse {
    private final int total;
    private final List<FavoriteResponse> favorites;

    public getFavoriteResponse(int total, List<FavoriteResponse> favorites) {
        this.total = total;
        this.favorites = favorites;
    }
}
