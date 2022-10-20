package com.rousing.mapper;

import com.rousing.pojo.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentMapper {
    List<Comment> selectAllByArtId(@Param("artId") Integer artId);

    int insertComment(Comment comment);

    int hideComment(Comment comment);

}
