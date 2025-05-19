package cn.edu.xidian.tafei_mall.model.vo.Response.Role;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoleResponse {
    private final String roleId;
    private final String name;
    private final String description;
    private final PermissionResponse permission;

    public RoleResponse(String roleId, String name, String description, PermissionResponse permission) {
        this.roleId = roleId;
        this.name = name;
        this.description = description;
        this.permission = permission;
    }
}
