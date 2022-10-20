package com.rousing.service;

import com.rousing.mapper.CommentMapper;
import com.rousing.pojo.Comment;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CommentService {
    @Resource
    CommentMapper commentMapper;

    public List<Comment> allCommentByArticle(Integer artId) {
        return commentMapper.selectAllByArtId(artId);
    }

    public boolean addComment(Comment comment) {
        return commentMapper.insertComment(comment) > 0;
    }

    public boolean hideComment(Comment comment) {
        return commentMapper.hideComment(comment) > 0;
    }

}
