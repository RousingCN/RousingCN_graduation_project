package com.rousing.mapper;

import com.rousing.pojo.Collect;
import com.rousing.pojo.Comment;
import com.rousing.pojo.Like;
import com.rousing.pojo.View;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HistoryMapper {
    List<Like> selectLikeHistory(Like like);

    List<Comment> selectCommentHistory(Comment comment);

    List<Collect> selectCollectHistory(Collect collect);

    List<View> selectViewHistory(View view);
}
