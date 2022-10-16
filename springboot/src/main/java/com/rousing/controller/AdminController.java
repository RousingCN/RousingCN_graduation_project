package com.rousing.controller;

import com.rousing.config.Result;
import com.rousing.pojo.Module;
import com.rousing.pojo.User;
import com.rousing.service.AdminService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Resource
    AdminService adminService;

    @PostMapping("/selectUser")
    public Result<?> selectUser(@RequestBody User user) {
        List<User> userList = adminService.selectUser(user);
        if (userList != null) {
            return Result.success(userList);
        } else {
            return Result.error("-1", "获取用户失败");
        }
    }

    @PutMapping("/updateUser")
    public Result<?> updateUser(@RequestBody User user, HttpSession session) {
        User requestUser = (User) session.getAttribute("user");
        if (requestUser.getUserStatus() != 3) {
            return Result.error("-3", "该操作需要管理员权限");
        }
        if (user.getUsername() == null || user.getUserid() == null || user.getUserStatus() == null) {
            return Result.error("-2", "无修改目标，请刷新页面后尝试");
        }
        if (adminService.updateUserStatus(user)) {
            return Result.success();
        } else {
            return Result.error("-1", "修改用户状态失败");
        }
    }
    @PostMapping("/selectModule")
    public Result<?> selectModule(@RequestBody Module module) {
        List<Module> moduleList = adminService.selectModule(module);
        if (moduleList != null) {
            return Result.success(moduleList);
        } else {
            return Result.error("-1", "获取板块失败");
        }
    }

    @PutMapping("/updateModule")
    public Result<?> updateModule(@RequestBody Module module, HttpSession session) {
        System.out.println(module);
        User requestUser = (User) session.getAttribute("user");
        if (requestUser.getUserStatus() != 3) {
            return Result.error("-3", "该操作需要管理员权限");
        }
        if (module.getModuleName() == null || module.getModuleId() == null || module.getModuleStatus() == null) {
            return Result.error("-2", "无修改目标，请刷新页面后尝试");
        }
        if (adminService.updateModuleStatus(module)) {
            return Result.success();
        } else {
            return Result.error("-1", "修改板块状态失败");
        }
    }
}
