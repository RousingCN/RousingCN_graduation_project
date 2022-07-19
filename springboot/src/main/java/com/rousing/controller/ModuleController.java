package com.rousing.controller;

import com.rousing.config.Result;
import com.rousing.pojo.Article;
import com.rousing.pojo.Module;
import com.rousing.pojo.User;
import com.rousing.service.ArticleService;
import com.rousing.service.ModuleService;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/module")
public class ModuleController {
    @Resource
    ModuleService moduleService;
    @Resource
    ArticleService articleService;

    @GetMapping("/random")
    public Result<?> getRandomModules() {
        List<Module> all = moduleService.getRandom();
        return Result.success(all);
    }

    @GetMapping("/all")
    public Result<?> getAllModules() {
        List<Module> all = moduleService.getAll();
        return Result.success(all);
    }

    @GetMapping("/page/{page}")
    public Result<?> getPageModules(@PathVariable Integer page) {
        try {
            int count = moduleService.getCount();
            HashMap<String, Object> map = new HashMap<>();
            map.put("startLimit", (page - 1) * 10);
            map.put("endLimit", page * 10);
            List<Module> pageData = moduleService.getPage(map);
            map = new HashMap<>();
            map.put("count", count);
            map.put("pageData", pageData);
            return Result.success(map);
        } catch (Exception e) {
            return Result.error("-1", "获取数据失败");
        }
    }


    @PostMapping("/add")
    public Result<?> addModule(@RequestBody Module module, HttpSession session) {
        if (!module.getModuleAuthor().getUserid().equals(((User) session.getAttribute("user")).getUserid())) {
            return Result.error("-2", "用户信息异常，请重新登录后重试");
        }
        if (moduleService.moduleExist(module.getModuleName())) {
            return Result.error("-1", "板块已存在");
        }
        if (moduleService.addModule(module)) {
            return Result.success();
        } else {
            return Result.error("-3", "添加版块失败");
        }
    }

    @PostMapping("/articles/{pageNum}")
    public Result<?> getAllArticle(@RequestBody Module module, @PathVariable Integer pageNum) {
        HashMap<String, Object> map = new HashMap<>();
        int count=articleService.getAllCount();
        List<Article> list = articleService.getPageArticle(module.getModuleId(), pageNum);
        map.put("count", count);
        map.put("list", list);
        return Result.success(map);
    }
}
