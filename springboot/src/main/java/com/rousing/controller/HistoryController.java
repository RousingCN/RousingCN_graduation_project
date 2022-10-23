package com.rousing.controller;

import com.rousing.config.Result;
import com.rousing.pojo.*;
import com.rousing.service.HistoryService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/history")
public class HistoryController {

    @Resource
    HistoryService historyService;

    @PostMapping("/like")
    public Result<?> likeHistory(@RequestBody Like like, HttpSession session) {
        User user = (User) session.getAttribute("user");
        like.setLikeUser(user);
        try {
            return Result.success(historyService.selectLikeHistory(like));
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("-1", "获取点赞记录失败");
        }
    }

    @PostMapping("/comment")
    public Result<?> commentHistory(@RequestBody Comment comment) {
        try {
            return Result.success(historyService.selectCommentHistory(comment));
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("-1", "获取评论记录失败");
        }
    }

    @PostMapping("/collect")
    public Result<?> collectHistory(@RequestBody Collect collect) {
        try {
            return Result.success(historyService.selectCollectHistory(collect));
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("-1", "获取收藏记录失败");
        }
    }

    @PostMapping("/view")
    public Result<?> viewHistory(@RequestBody View view) {
        try {
            return Result.success(historyService.selectViewHistory(view));
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("-1", "获取浏览记录失败");
        }
    }
}
