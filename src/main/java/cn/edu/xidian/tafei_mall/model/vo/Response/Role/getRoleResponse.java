package cn.edu.xidian.tafei_mall.model.vo.Response.Role;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class getRoleResponse {
    private final List<RoleResponse> roles;

    public getRoleResponse(List<RoleResponse> roles) {
        this.roles = roles;
    }
}
