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
        // 获取用户信息
        User user = (User) session.getAttribute("user");
        like.setLikeUser(user);
        try {
            // 查询点赞记录
            return Result.success(historyService.selectLikeHistory(like));
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("-1", "获取点赞记录失败");
        }
    }

    @PostMapping("/comment")
    public Result<?> commentHistory(@RequestBody Comment comment,HttpSession session) {
        // 获取用户信息
        User user = (User) session.getAttribute("user");
        comment.setComUser(user);
        try {
            // 查询评论记录
            return Result.success(historyService.selectCommentHistory(comment));
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("-1", "获取评论记录失败");
        }
    }

    @PostMapping("/collect")
    public Result<?> collectHistory(@RequestBody Collect collect,HttpSession session) {
        // 获取用户信息
        User user = (User) session.getAttribute("user");
        collect.setColUser(user);
        try {
            // 查询收藏记录
            return Result.success(historyService.selectCollectHistory(collect));
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("-1", "获取收藏记录失败");
        }
    }

    @PostMapping("/view")
    public Result<?> viewHistory(@RequestBody View view,HttpSession session) {
        // 获取用户信息
        User user = (User) session.getAttribute("user");
        view.setViewUser(user);
        try {
            // 查询浏览记录
            return Result.success(historyService.selectViewHistory(view));
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("-1", "获取浏览记录失败");
        }
    }
}
