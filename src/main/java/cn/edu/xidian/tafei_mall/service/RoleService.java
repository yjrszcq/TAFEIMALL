package cn.edu.xidian.tafei_mall.service;

import cn.edu.xidian.tafei_mall.model.entity.User;
import cn.edu.xidian.tafei_mall.model.vo.Response.Role.createRoleResponse;
import cn.edu.xidian.tafei_mall.model.vo.Response.Role.getRoleResponse;
import cn.edu.xidian.tafei_mall.model.vo.RoleCreateVO;
import cn.edu.xidian.tafei_mall.model.vo.RolePermissionVO;
import cn.edu.xidian.tafei_mall.model.vo.RoleUpdateVO;

public interface RoleService {
    int verifyUserPermission(String userId, String permissionKey);
    int verifyUserPermission(User user, String permissionKey);
    getRoleResponse getRole();
    getRoleResponse getRole(String roleId);
    createRoleResponse createRole(RoleCreateVO roleCreateVO);
    boolean updateRole(RoleUpdateVO roleUpdateVO, String roleId);
    boolean updateRolePermission(RolePermissionVO rolePermissionVO, String roleId);
    boolean deleteRole(String roleId);
}
