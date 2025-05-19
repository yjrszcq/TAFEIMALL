package cn.edu.xidian.tafei_mall.model.vo.Response.Role;

import cn.edu.xidian.tafei_mall.model.entity.RolePermission;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoleResponse {
    private final String roleId;
    private final String name;
    private final String description;
    private final RolePermission permission;

    public RoleResponse(String roleId, String name, String description, RolePermission permission) {
        this.roleId = roleId;
        this.name = name;
        this.description = description;
        this.permission = permission;
    }
}
