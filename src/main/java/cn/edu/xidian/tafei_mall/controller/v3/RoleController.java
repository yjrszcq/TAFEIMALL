package cn.edu.xidian.tafei_mall.controller.v3;

import cn.edu.xidian.tafei_mall.model.entity.User;
import cn.edu.xidian.tafei_mall.model.vo.Response.Order.MessageResponse;
import cn.edu.xidian.tafei_mall.model.vo.Response.Role.createRoleResponse;
import cn.edu.xidian.tafei_mall.model.vo.Response.Role.getRoleResponse;
import cn.edu.xidian.tafei_mall.model.vo.RoleCreateVO;
import cn.edu.xidian.tafei_mall.model.vo.RolePermissionVO;
import cn.edu.xidian.tafei_mall.model.vo.RoleUpdateVO;
import cn.edu.xidian.tafei_mall.service.RoleService;
import cn.edu.xidian.tafei_mall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api/v3/roles")
public class RoleController {
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserService userService;

    @GetMapping("")
    public ResponseEntity<?> getRoles(@RequestHeader("Session-Id") String sessionId,
                                      @RequestParam(required = false, defaultValue = "-1") String roleId){
        try{
            if(Objects.equals(roleId, "-1")){
                getRoleResponse response = roleService.getRole(roleId);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                getRoleResponse response = roleService.getRole(roleId);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createRole(@RequestHeader("Session-Id") String sessionId,
                                        @RequestBody RoleCreateVO roleCreateVO) {
        try {
            if (sessionId == null || sessionId.isEmpty()) {
                return ResponseEntity.badRequest().body(new MessageResponse("Session-Id不能为空"));
            }
            User user = userService.getUserInfo(sessionId);
            if (user == null) {
                return ResponseEntity.badRequest().body(new MessageResponse("用户不存在"));
            }
            if (roleService.verifyUserPermission(user, "role") < 2) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse("没有权限"));
            }
            createRoleResponse response = roleService.createRole(roleCreateVO);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @PostMapping("/update/{roleId}")
    public ResponseEntity<?> updateRole(@RequestHeader("Session-Id") String sessionId,
                                        @PathVariable String roleId,
                                        @RequestBody RoleUpdateVO roleUpdateVO) {
        try {
            if (sessionId == null || sessionId.isEmpty()) {
                return ResponseEntity.badRequest().body(new MessageResponse("Session-Id不能为空"));
            }
            User user = userService.getUserInfo(sessionId);
            if (user == null) {
                return ResponseEntity.badRequest().body(new MessageResponse("用户不存在"));
            }
            if (roleService.verifyUserPermission(user, "role") < 2) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse("没有权限"));
            }
            if (roleService.updateRole(roleUpdateVO, roleId)){
                return ResponseEntity.ok(new MessageResponse("角色更新成功"));
            } else {
                return ResponseEntity.badRequest().body(new MessageResponse("角色更新失败"));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @PostMapping("/update/{roleId}/permission")
    public ResponseEntity<?> updateRolePermission(@RequestHeader("Session-Id") String sessionId,
                                                  @PathVariable String roleId,
                                                  @RequestBody RolePermissionVO rolePermissionVO) {
        try {
            if (sessionId == null || sessionId.isEmpty()) {
                return ResponseEntity.badRequest().body(new MessageResponse("Session-Id不能为空"));
            }
            User user = userService.getUserInfo(sessionId);
            if (user == null) {
                return ResponseEntity.badRequest().body(new MessageResponse("用户不存在"));
            }
            if (roleService.verifyUserPermission(user, "role") < 2) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse("没有权限"));
            }
            if (roleService.updateRolePermission(rolePermissionVO, roleId)){
                return ResponseEntity.ok(new MessageResponse("角色权限更新成功"));
            } else {
                return ResponseEntity.badRequest().body(new MessageResponse("角色权限更新失败"));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @DeleteMapping("/{roleId}")
    public ResponseEntity<?> deleteRole(@RequestHeader("Session-Id") String sessionId,
                                        @PathVariable String roleId) {
        try {
            if (sessionId == null || sessionId.isEmpty()) {
                return ResponseEntity.badRequest().body(new MessageResponse("Session-Id不能为空"));
            }
            User user = userService.getUserInfo(sessionId);
            if (user == null) {
                return ResponseEntity.badRequest().body(new MessageResponse("用户不存在"));
            }
            if (roleService.verifyUserPermission(user, "role") < 3) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse("没有权限"));
            }
            if (roleService.deleteRole(roleId)){
                return ResponseEntity.ok(new MessageResponse("角色删除成功"));
            } else {
                return ResponseEntity.badRequest().body(new MessageResponse("角色删除失败"));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }
}
