package cn.edu.xidian.tafei_mall.model.vo.Response.Role;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PermissionResponse {
    private final int cartPermission;
    private final int orderPermission;
    private final int productPermission;
    private final int addressPermission;
    private final int reviewPermission;
    private final int favoritePermission;
    private final int rolePermission;
    private final int userPermission;

    public PermissionResponse(int cartPermission, int orderPermission, int productPermission, int addressPermission, int reviewPermission, int favoritePermission, int rolePermission, int userPermission) {
        this.cartPermission = cartPermission;
        this.orderPermission = orderPermission;
        this.productPermission = productPermission;
        this.addressPermission = addressPermission;
        this.reviewPermission = reviewPermission;
        this.favoritePermission = favoritePermission;
        this.rolePermission = rolePermission;
        this.userPermission = userPermission;
    }
}
