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
    private static final String ip = "http://localhost";

    @PostMapping("/avatar/save")
    public Result<?> saveAvatar(MultipartFile file, HttpSession session) {
        String originalFilename = file.getOriginalFilename();
        String uuid = IdUtil.fastSimpleUUID();
        String rootFilePath = System.getProperty("user.dir") + "/springboot/src/main/resources/static/avatar/" + uuid + "_" + originalFilename;
        try {
            //写入文件
            FileUtil.writeBytes(file.getBytes(), rootFilePath);
        } catch (IOException e) {
            return Result.error("-1", "更新头像失败");
        }
        //更新保存的信息
        User user = (User) session.getAttribute("user");
        user.setUserImg(ip + ":" + port + "/avatar/" + uuid + "_" + originalFilename);
        if (userService.updateUser(user)) {
            return Result.success(userService.getOne(user));
        }
        return Result.error("0", "更新用户信息失败");
    }

    @GetMapping("/avatar/{fileName}")
    public void loadAvatar(@PathVariable String fileName, HttpServletResponse response) {
        OutputStream os;
        String basePath = System.getProperty("user.dir") + "/springboot/src/main/resources/static/avatar/";
        List<String> fileNames = FileUtil.listFileNames(basePath);
        String file = fileNames.stream().filter(Name -> Name.contains(fileName)).findAny().orElse("");
        try {
            if (StrUtil.isNotEmpty(file)) {
                response.addHeader("Content-Disposition","attachment;filename="+ URLEncoder.encode(file,"UTF-8"));
                System.out.println(file);
                if (file.endsWith(".jpeg") || file.endsWith(".jpg")) {
                    response.setContentType("image/jpeg");
                } else if (file.endsWith(".png")) {
                    response.setContentType("image/png");
                }
                byte[] bytes = FileUtil.readBytes(basePath + file);
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
