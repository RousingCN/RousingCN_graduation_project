package com.rousing.service;

import com.rousing.mapper.AdminMapper;
import com.rousing.pojo.Article;
import com.rousing.pojo.Comment;
import com.rousing.pojo.Module;
import com.rousing.pojo.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AdminService {
    @Resource
    AdminMapper adminMapper;

    public List<User> selectUser(User user) {
        if ("".equals(user.getUsername())) {
            user.setUsername(null);
        }
        return adminMapper.selectUser(user);
    }

    public boolean updateUserStatus(User user) {
        return adminMapper.updateUser(user) > 0;
    }


    public List<Module> selectModule(Module module) {
        return adminMapper.selectModule(module);
    }

    public boolean updateModuleStatus(Module module) {
        return adminMapper.updateModule(module) > 0;
    }


    public List<Article> selectArticle(Article article) {
        return adminMapper.selectArticle(article);
    }

    public boolean updateArticleStatus(Article article) {
        return adminMapper.updateArticle(article) > 0;
    }


    public List<Comment> selectComment(Comment comment) {
        return adminMapper.selectComment(comment);
    }

    public boolean updateCommentStatus(Comment comment) {
        return adminMapper.updateComment(comment) > 0;
    }

}
