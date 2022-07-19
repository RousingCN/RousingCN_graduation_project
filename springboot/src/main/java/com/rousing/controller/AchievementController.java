package com.rousing.controller;

import com.rousing.config.Result;
import com.rousing.pojo.Article;
import com.rousing.pojo.User;
import com.rousing.service.AchievementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/Achievement")
@Slf4j
public class AchievementController {
    @Resource
    AchievementService achievementService;

    @PostMapping("/user")
    public Result<?> userAchievement(@RequestBody User user) {
        try {
            return Result.success(achievementService.getUserAchievement(user.getUserid()));
        } catch (Exception err) {
            log.error(err.getMessage());
            return Result.error("-1", "获取用户信息失败");
        }
    }

    @PostMapping("/article")
    public Result<?> articleAchievement(@RequestBody Article article) {
        try {
            return Result.success(achievementService.getArticleAchievement(article.getArtId()));
        } catch (Exception err) {
            log.error(err.getMessage());
            return Result.error("-1", "获取帖子信息失败");
        }
    }
}
