package com.rousing.controller;

import com.rousing.config.Result;
import com.rousing.pojo.Article;
import com.rousing.pojo.User;
import com.rousing.service.ArticleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Resource
    ArticleService articleService;

    @PostMapping("/createArticle")
    public Result<?> newArticle(@RequestBody Article article, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (article.getArtModule() == 1 && user.getUserStatus() != 3) {
            return Result.error("-3", "普通用户无法在官方板块内发帖");
        }
        if (!article.getArtAuthor().getUserid().equals(user.getUserid())) {
            return Result.error("-2", "用户信息异常，请重新登录后重试");
        }
        if (articleService.addArticle(article)) {
            return Result.success();
        } else {
            return Result.error("-1", "添加失败");
        }
    }

}
