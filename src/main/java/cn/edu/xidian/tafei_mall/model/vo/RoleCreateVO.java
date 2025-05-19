package cn.edu.xidian.tafei_mall.model.vo;

import cn.edu.xidian.tafei_mall.model.entity.RolePermission;
import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class RoleCreateVO {
    private String name;
    private String description;
    private RolePermission permission;
}
