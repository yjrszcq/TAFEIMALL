package cn.edu.xidian.tafei_mall.model.vo;

import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class RolePermissionVO {
    private int cartPermission;
    private int orderPermission;
    private int productPermission;
    private int addressPermission;
    private int reviewPermission;
    private int favoritePermission;
    private int rolePermission;
    private int userPermission;
}