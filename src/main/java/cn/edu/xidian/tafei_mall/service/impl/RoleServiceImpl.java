package cn.edu.xidian.tafei_mall.service.impl;

import cn.edu.xidian.tafei_mall.mapper.PermissionMapper;
import cn.edu.xidian.tafei_mall.mapper.RoleMapper;
import cn.edu.xidian.tafei_mall.mapper.UserMapper;
import cn.edu.xidian.tafei_mall.model.entity.Permission;
import cn.edu.xidian.tafei_mall.model.entity.Role;
import cn.edu.xidian.tafei_mall.model.entity.User;
import cn.edu.xidian.tafei_mall.model.vo.Response.Role.PermissionResponse;
import cn.edu.xidian.tafei_mall.model.vo.Response.Role.RoleResponse;
import cn.edu.xidian.tafei_mall.model.vo.Response.Role.createRoleResponse;
import cn.edu.xidian.tafei_mall.model.vo.Response.Role.getRoleResponse;
import cn.edu.xidian.tafei_mall.model.vo.RoleCreateVO;
import cn.edu.xidian.tafei_mall.model.vo.RolePermissionVO;
import cn.edu.xidian.tafei_mall.model.vo.RoleUpdateVO;
import cn.edu.xidian.tafei_mall.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public int verifyUserPermission(String userId, String permissionKey){
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        return verifyUserPermission(user, permissionKey);
    }

    @Override
    public int verifyUserPermission(User user, String permissionKey){
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        Role role = roleMapper.selectById(user.getRoleId());
        if (role == null) {
            throw new IllegalArgumentException("Role not found");
        }
        Permission permission = permissionMapper.selectById(role.getPermissionId());
        if (permission == null) {
            throw new IllegalArgumentException("Permission not found");
        }
        switch (permissionKey){
            case "address" -> {return permission.getAddressPermission();}
            case "cart" -> {return permission.getCartPermission();}
            case "order" -> {return permission.getOrderPermission();}
            case "product" -> {return permission.getProductPermission();}
            case "review" -> {return permission.getReviewPermission();}
            case "favorite" -> {return permission.getFavoritePermission();}
            case "role" -> {return permission.getRolePermission();}
            case "user" -> {return permission.getUserPermission();}
        }
        throw new IllegalArgumentException("Permission not found");
    }

    @Override
    public getRoleResponse getRole() {
        List<Role> roles = roleMapper.selectList(null);
        List<RoleResponse> roleResponses = new ArrayList<>();
        for(Role role : roles){
            if (role == null){
                continue;
            }
            Permission permission = permissionMapper.selectById(role.getPermissionId());
            if (permission == null){
                continue;
            }
            PermissionResponse permissionResponse = new PermissionResponse(
                    permission.getCartPermission(),
                    permission.getOrderPermission(),
                    permission.getProductPermission(),
                    permission.getAddressPermission(),
                    permission.getReviewPermission(),
                    permission.getFavoritePermission(),
                    permission.getRolePermission(),
                    permission.getUserPermission());
            roleResponses.add(new RoleResponse(role.getRoleId(), role.getName(), role.getDescription(), permissionResponse));
        }
        return new getRoleResponse(roleResponses);
    }

    @Override
    public getRoleResponse getRole(String roleId) {
        Role role = roleMapper.selectById(roleId);
        List<RoleResponse> roleResponses = new ArrayList<>();
        if (role == null){
            throw new IllegalArgumentException("Role not found");
        }
        Permission permission = permissionMapper.selectById(role.getPermissionId());
        if (permission == null){
            throw new IllegalArgumentException("Permission not found");
        }
        PermissionResponse permissionResponse = new PermissionResponse(
                permission.getCartPermission(),
                permission.getOrderPermission(),
                permission.getProductPermission(),
                permission.getAddressPermission(),
                permission.getReviewPermission(),
                permission.getFavoritePermission(),
                permission.getRolePermission(),
                permission.getUserPermission());
        roleResponses.add(new RoleResponse(role.getRoleId(), role.getName(), role.getDescription(), permissionResponse));
        return new getRoleResponse(roleResponses);
    }

    @Override
    public createRoleResponse createRole(RoleCreateVO roleCreateVO) {
        Permission permission = new Permission();
        permission.setPermissionId(UUID.randomUUID().toString());
        permission.setAddressPermission(roleCreateVO.getPermission().getAddressPermission());
        permission.setCartPermission(roleCreateVO.getPermission().getCartPermission());
        permission.setOrderPermission(roleCreateVO.getPermission().getOrderPermission());
        permission.setProductPermission(roleCreateVO.getPermission().getProductPermission());
        permission.setReviewPermission(roleCreateVO.getPermission().getReviewPermission());
        permission.setFavoritePermission(roleCreateVO.getPermission().getFavoritePermission());
        permission.setRolePermission(roleCreateVO.getPermission().getRolePermission());
        permission.setUserPermission(roleCreateVO.getPermission().getUserPermission());
        permission.setCreatedAt(LocalDateTime.now());
        permission.setUpdatedAt(LocalDateTime.now());
        permissionMapper.insert(permission);

        Role role = new Role();
        role.setRoleId(UUID.randomUUID().toString());
        role.setName(roleCreateVO.getName());
        role.setDescription(roleCreateVO.getDescription());
        role.setPermissionId(permission.getPermissionId());
        role.setCreatedAt(LocalDateTime.now());
        role.setUpdatedAt(LocalDateTime.now());
        roleMapper.insert(role);
        return new createRoleResponse(role.getRoleId());
    }

    @Override
    public boolean updateRole(RoleUpdateVO roleUpdateVO, String roleId) {
        Role role = roleMapper.selectById(roleId);
        if (role == null) {
            throw new RuntimeException("Role not found");
        }
        if (roleUpdateVO.getName() == null) {
            throw new IllegalArgumentException("Role name cannot be null");
        }
        role.setName(roleUpdateVO.getName());
        role.setDescription(roleUpdateVO.getDescription());
        role.setUpdatedAt(LocalDateTime.now());
        roleMapper.updateById(role);
        return true;
    }

    @Override
    public boolean updateRolePermission(RolePermissionVO rolePermissionVO, String roleId) {
        Role role = roleMapper.selectById(roleId);
        if (role == null) {
            throw new RuntimeException("Role not found");
        }
        Permission permission = permissionMapper.selectById(role.getPermissionId());
        if (permission == null) {
            throw new RuntimeException("Permission not found");
        }
        permission.setAddressPermission(rolePermissionVO.getAddressPermission());
        permission.setCartPermission(rolePermissionVO.getCartPermission());
        permission.setOrderPermission(rolePermissionVO.getOrderPermission());
        permission.setProductPermission(rolePermissionVO.getProductPermission());
        permission.setReviewPermission(rolePermissionVO.getReviewPermission());
        permission.setFavoritePermission(rolePermissionVO.getFavoritePermission());
        permission.setRolePermission(rolePermissionVO.getRolePermission());
        permission.setUserPermission(rolePermissionVO.getUserPermission());
        permission.setUpdatedAt(LocalDateTime.now());
        permissionMapper.updateById(permission);
        return true;
    }

    @Override
    public boolean deleteRole(String roleId) {
        Role role = roleMapper.selectById(roleId);
        if (role == null) {
            throw new RuntimeException("Role not found");
        }
        String permissionId = role.getPermissionId();
        Permission permission = permissionMapper.selectById(permissionId);
        if (permission == null) {
            throw new RuntimeException("Permission not found");
        }
        roleMapper.deleteById(roleId);
        permissionMapper.deleteById(permissionId);
        return true;
    }
}