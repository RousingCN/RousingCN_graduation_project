package com.rousing.controller;

import com.rousing.config.Result;
import com.rousing.pojo.Pwd;
import com.rousing.pojo.User;
import com.rousing.service.UserService;
import com.rousing.utils.MD5Utils;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;


@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    UserService userService;

    @PostMapping("/checkUsername")
    public Result<?> checkUsername(@RequestBody User user) {
        //检查用户名是否被使用
        if (userService.userExist(user.getUsername())) {
            return Result.error("-1", "用户名已被占用");
        } else {
            return Result.success();
        }
    }

    @PostMapping("/register")
    public Result<?> add(@RequestBody User user) {
        //检查用户名是否被使用
        if (userService.userExist(user.getUsername())) {
            return Result.error("-1", "用户名已被占用");
        }
        // 将密码进行md5加密
        user.setUserPassword(MD5Utils.inputPassToFromPass(user.getUserPassword()));
        //添加用户
        if (userService.addUser(user)) {
            return Result.success();
        } else {
            return Result.error("-1", "添加失败");
        }
    }

    @PostMapping("/login")
    public Result<?> login(@RequestBody User user, HttpSession session) {
        // md5加密用户密码
        user.setUserPassword(MD5Utils.inputPassToFromPass(user.getUserPassword()));
        // 根据前端发送的用用户名和密码查询数据库里是否有该用户
        User one = userService.getOne(user);
        if (one != null) {
            // 隐藏重要信息后发送
            // UserUtils.hideUserInfo(one);
            session.setAttribute("user", one);
            return Result.success(one);
        } else {
            return Result.error("-1", "用户名或密码不正确");
        }
    }

    @DeleteMapping("/loginOut")
    public Result<?> loginOut(HttpSession session) {
        session.invalidate();
        return Result.success();
    }

    @PutMapping("/update")
    public Result<?> update(@RequestBody User user) {
        // 更新用户信息
        if (userService.updateUser(user)) {
            // 创建新的User类型对象保存更新过后的信息并返回
            User newUser = new User();
            newUser.setUserid(user.getUserid());
            return Result.success(userService.getOne(newUser));
        } else {
            return Result.error("-1", "更新数据失败");
        }
    }

    @PutMapping("/update/password")
    public Result<?> updatePwd(@RequestBody Pwd pwd, HttpSession session) {
        // 查询提交信息的账号是否正确
        User user = (User) session.getAttribute("user");
        // 用户信息是否一致
        if (!user.getUserid().equals(pwd.getUserid())) {
            return Result.error("-2", "请重新登录账号后重试");
        }
        // 查询旧密码是否符合
        user.setUserPassword(MD5Utils.inputPassToFromPass(pwd.getOldPassword()));
        // 查询是否存在该用户
        if (userService.getOne(user) == null) {
            return Result.error("-1", "旧密码不正确");
        }
        // 开始修改密码
        user.setUserPassword(MD5Utils.inputPassToFromPass(pwd.getNewPassword()));
        if (userService.updateUser(user)) {
            // 初始化session对象
            session.invalidate();
            return Result.success();
        }
        return Result.error("-3", "更新密码时出错");
    }

}
