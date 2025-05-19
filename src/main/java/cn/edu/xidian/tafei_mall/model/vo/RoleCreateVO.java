package cn.edu.xidian.tafei_mall.model.vo;

import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class RoleCreateVO {
    private String name;
    private String description;
    private RolePermissionVO permission;
}
