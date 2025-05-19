package cn.edu.xidian.tafei_mall.service.impl;

import cn.edu.xidian.tafei_mall.mapper.RoleMapper;
import cn.edu.xidian.tafei_mall.mapper.UserMapper;
import cn.edu.xidian.tafei_mall.model.entity.Role;
import cn.edu.xidian.tafei_mall.model.entity.RolePermission;
import cn.edu.xidian.tafei_mall.model.entity.User;
import cn.edu.xidian.tafei_mall.model.vo.Response.Role.RoleResponse;
import cn.edu.xidian.tafei_mall.model.vo.Response.Role.createRoleResponse;
import cn.edu.xidian.tafei_mall.model.vo.Response.Role.getRoleResponse;
import cn.edu.xidian.tafei_mall.model.vo.RoleCreateVO;
import cn.edu.xidian.tafei_mall.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public getRoleResponse getRole() {
        List<Role> roles = roleMapper.selectList(null);
        List<RoleResponse> roleResponses = new ArrayList<>();
        for(Role role : roles){
            if (role == null){
                continue;
            }
            roleResponses.add(new RoleResponse(role.getRoleId(), role.getName(), role.getDescription(), role.getPermission()));
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
        roleResponses.add(new RoleResponse(role.getRoleId(), role.getName(), role.getDescription(), role.getPermission()));
        return new getRoleResponse(roleResponses);
    }

    @Override
    public createRoleResponse createRole(RoleCreateVO roleCreateVO) {
        Role role = new Role();
        role.setRoleId(UUID.randomUUID().toString());
        role.setName(roleCreateVO.getName());
        role.setDescription(roleCreateVO.getDescription());
        role.setPermission(roleCreateVO.getPermission());
        role.setCreatedAt(LocalDateTime.now());
        role.setUpdatedAt(LocalDateTime.now());
        roleMapper.insert(role);
        return new createRoleResponse(role.getRoleId());
    }

    @Override
    public boolean updateRole(RoleCreateVO roleVO, String roleId) {
        Role role = roleMapper.selectById(roleId);
        if (role == null) {
            throw new RuntimeException("Role not found");
        }
        role.setUpdatedAt(LocalDateTime.now());
        roleMapper.updateById(role);
        return true;
    }

    @Override
    public boolean deleteRole(String roleId) {
        Role role = roleMapper.selectById(roleId);
        if (role == null) {
            throw new RuntimeException("Role not found");
        }
        roleMapper.deleteById(roleId);
        return true;
    }

    @Override
    public boolean verifyUserPermission(String userId, String permissionKey) throws NoSuchFieldException, IllegalAccessException {
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        return verifyUserPermission(user, permissionKey);
    }

    @Override
    public boolean verifyUserPermission(User user, String permissionKey) throws NoSuchFieldException, IllegalAccessException {
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        RolePermission permission = roleMapper.selectById(user.getRoleId()).getPermission();
        if (permission == null) return false;
        String[] parts = permissionKey.split(":");

        /*
        Field typeField = permission.getClass().getDeclaredField(parts[0]);
        typeField.setAccessible(true);
        Object typeObj = typeField.get(permission);
        if (typeObj == null) return false;

        Field permField = typeObj.getClass().getDeclaredField(parts[1]);
        permField.setAccessible(true);

        return (boolean) permField.get(typeObj);
        */

        switch (parts[0]){
            case "address" -> {
                switch (parts[1]){
                    case "get" -> {return permission.getAddress().isGet();}
                    case "edit" -> {return permission.getAddress().isEdit();}
                }
            }
            case "cart" -> {
                switch (parts[1]){
                    case "get" -> {return permission.getCart().isGet();}
                    case "edit" -> {return permission.getCart().isEdit();}
                }
            }
            case "order" -> {
                switch (parts[1]){
                    case "get" -> {return permission.getOrder().isGet();}
                    case "create" -> {return permission.getOrder().isCreate();}
                    case "pay" -> {return permission.getOrder().isPay();}
                    case "ship" -> {return permission.getOrder().isShip();}
                    case "finish" -> {return permission.getOrder().isFinish();}
                    case "cancel" -> {return permission.getOrder().isCancel();}
                    case "delete" -> {return permission.getOrder().isDelete();}
                    case "update" -> {return permission.getOrder().isUpdate();}
                }
            }
            case "review" -> {
                switch (parts[1]){
                    case "edit" -> {return permission.getReview().isEdit();}
                    case "delete" -> {return permission.getReview().isDelete();}
                }
            }
            case "favorite" -> {
                switch (parts[1]){
                    case "get" -> {return permission.getFavorite().isGet();}
                    case "edit" -> {return permission.getFavorite().isEdit();}
                    case "delete" -> {return permission.getFavorite().isDelete();}
                }
            }
            case "product" -> {
                switch (parts[1]){
                    case "edit" -> {return permission.getProduct().isEdit();}
                    case "delete" -> {return permission.getProduct().isDelete();}
                }
            }
            case "promotion" -> {
                switch (parts[1]){
                    case "edit" -> {return permission.getPromotion().isEdit();}
                    case "delete" -> {return permission.getPromotion().isDelete();}
                }
            }
            case "report" -> {
                switch (parts[1]){
                    case "generate" -> {return permission.getReport().isGenerate();}
                }
            }
        }
        return false;
    }
}