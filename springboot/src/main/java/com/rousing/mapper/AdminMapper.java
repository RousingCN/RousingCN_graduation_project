package com.rousing.mapper;

import com.rousing.pojo.Article;
import com.rousing.pojo.Comment;
import com.rousing.pojo.Module;
import com.rousing.pojo.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminMapper {
    List<User> selectUser(User user);
    int updateUser(User user);

    List<Module> selectModule(Module module);
    int updateModule(Module module);

    List<Article> selectArticle(Article article);
    int updateArticle(Article article);

    List<Comment> selectComment(Comment comment);
    int updateComment(Comment comment);

}
