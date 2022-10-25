package com.rousing.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.rousing.config.Result;
import com.rousing.pojo.User;
import com.rousing.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

@RestController
@RequestMapping("/file")
public class FileController {
    @Resource
    UserService userService;
    @Value("${server.port}")
    private String port;
    private static final String IP = "http://localhost";

    @PostMapping("/avatar/save")
    public Result<?> saveAvatar(MultipartFile file, HttpSession session) {
        // 获取前端传来的文件名
        String originalFilename = file.getOriginalFilename();
        // 生成UUID
        String uuid = IdUtil.fastSimpleUUID();
        // 拼接保存路径
        String rootFilePath = System.getProperty("user.dir") + "/springboot/src/main/resources/static/avatar/" + uuid + "_" + originalFilename;
        try {
            //写入文件
            FileUtil.writeBytes(file.getBytes(), rootFilePath);
        } catch (IOException e) {
            return Result.error("-1", "更新头像失败");
        }
        // 获取当前用户
        User user = (User) session.getAttribute("user");
        // 更新头像的路径信息
        user.setUserAvatar(IP + ":" + port + "/avatar/" + uuid + "_" + originalFilename);
        if (userService.updateUser(user)) {
            return Result.success();
        }
        return Result.error("0", "更新用户信息失败");
    }

    @GetMapping("/avatar/{fileName}")
    public void loadAvatar(@PathVariable String fileName, HttpServletResponse response) {
        OutputStream os;
        // 获取头像所在的文件夹路径
        String basePath = System.getProperty("user.dir") + "/springboot/src/main/resources/static/avatar/";
        // 获取该路径下的所有文件名称
        List<String> fileNames = FileUtil.listFileNames(basePath);
        // 查找符合名称的头像文件路径
        String file = fileNames.stream().filter(Name -> Name.contains(fileName)).findAny().orElse("");
        try {
            // 判断是否找到了头像文件路径
            if (StrUtil.isNotEmpty(file)) {
                // 设置响应头
                response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(file, "UTF-8"));
                // 判断该路径的文件是否为PNG格式
                if (file.endsWith(".png")) {
                    // 设置文件类型
                    response.setContentType("image/png");
                }
                // 获取文件字节流
                byte[] bytes = FileUtil.readBytes(basePath + file);
                // 输出字节流
                os = response.getOutputStream();
                os.write(bytes);
                os.flush();
                os.close();
            }
        } catch (Exception e) {
            System.out.println("文件下载失败");
        }
    }
}
