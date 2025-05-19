package cn.edu.xidian.tafei_mall.service;

import cn.edu.xidian.tafei_mall.model.entity.User;
import cn.edu.xidian.tafei_mall.model.vo.Response.Role.createRoleResponse;
import cn.edu.xidian.tafei_mall.model.vo.Response.Role.getRoleResponse;
import cn.edu.xidian.tafei_mall.model.vo.RoleCreateVO;

public interface RoleService {
    boolean verifyUserPermission(String userId, String permissionKey) throws NoSuchFieldException, IllegalAccessException;
    boolean verifyUserPermission(User user, String permissionKey) throws NoSuchFieldException, IllegalAccessException;
    getRoleResponse getRole();
    getRoleResponse getRole(String roleId);
    createRoleResponse createRole(RoleCreateVO roleCreateVO);
    boolean updateRole(RoleCreateVO roleVO, String roleId);
    boolean deleteRole(String roleId);
}
