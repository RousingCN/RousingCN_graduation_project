package com.rousing.controller;

import com.rousing.config.Result;
import com.rousing.pojo.Article;
import com.rousing.pojo.Comment;
import com.rousing.pojo.User;
import com.rousing.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/comment")
@Slf4j
public class CommentController {
    @Resource
    CommentService commentService;

    @PostMapping("/all")
    public Result<?> allArtComment(@RequestBody Article article) {
        List<Comment> commentList = commentService.allCommentByArticle(article.getArtId());
        return Result.success(commentList);
    }

    @PostMapping("/add")
    public Result<?> addArtComment(@RequestBody Comment comment, HttpSession session) {
        User user = (User) session.getAttribute("user");
        comment.setComUser(user);
        if (commentService.addComment(comment)) {
            return Result.success();
        } else {
            return Result.error("-1", "发表评论时发生了一个意外，请稍后再试");
        }
    }
    @PostMapping("/hide")
    public Result<?> hideArtComment(@RequestBody Comment comment, HttpSession session) {
        User user = (User) session.getAttribute("user");
        comment.setComUser(user);
        if (commentService.hideComment(comment)) {
            return Result.success();
        } else {
            return Result.error("-1", "删除评论时发生了一个意外，请稍后再试");
        }
    }
}
