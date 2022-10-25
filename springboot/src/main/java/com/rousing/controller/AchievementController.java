package com.rousing.controller;

import com.rousing.config.Result;
import com.rousing.pojo.Article;
import com.rousing.pojo.ArticleAchievement;
import com.rousing.pojo.User;
import com.rousing.service.AchievementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/Achievement")
@Slf4j
public class AchievementController {
    @Resource
    AchievementService achievementService;

    @PostMapping("/article")
    public Result<?> articleAchievement(@RequestBody Article article, HttpSession session) {
        // 获取当前用户的信息
        User user = (User) session.getAttribute("user");
        try {
            // 获取帖子的更多数据
            return Result.success(achievementService.getArticleAchievement(article.getArtId(), user.getUserid()));
        } catch (Exception err) {
            log.error(err.getMessage());
            return Result.error("-1", "获取帖子信息失败");
        }
    }

    @PostMapping("/userLikeArticle")
    public Result<?> userLikeArticle(@RequestBody ArticleAchievement achievement, HttpSession session) {
        // 获取当前用户信息
        User user = (User) session.getAttribute("user");
        // 传递数据，点赞与取消点赞逻辑交由service层处理
        if (achievementService.likeChange(achievement.isLike_it(), achievement.getArticleId(), user.getUserid())) {
            return Result.success(!achievement.isLike_it());
        } else {
            return Result.error("-1", "服务器处理点赞时出现了一个错误，请稍后再试");
        }
    }

    @PostMapping("/userCollectArticle")
    public Result<?> userCollectArticle(@RequestBody ArticleAchievement achievement, HttpSession session) {
        // 获取当前用户信息
        User user = (User) session.getAttribute("user");
        // 传递数据，点赞与取消点赞逻辑交由service层处理
        if (achievementService.collectChange(achievement.isCollect_it(), achievement.getArticleId(), user.getUserid())) {
            return Result.success(!achievement.isCollect_it());
        } else {
            return Result.error("-1", "服务器处理收藏时出现了一个错误，请稍后再试");
        }
    }

    @PostMapping("/userViewArticle")
    public Result<?> userViewArticle(@RequestBody ArticleAchievement achievement, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (achievementService.viewArticle(achievement.getArticleId(), user.getUserid())) {
            return Result.success(!achievement.isCollect_it());
        } else {
            return Result.error("-1", "发生意外，本次浏览不会被记录");
        }
    }
}
