package cn.edu.xidian.tafei_mall.model.vo.Response.Role;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class createRoleResponse {
    private final String roleId;

    public createRoleResponse(String roleId) {
        this.roleId = roleId;
    }
}
