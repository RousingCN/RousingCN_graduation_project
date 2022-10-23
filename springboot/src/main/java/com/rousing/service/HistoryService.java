package com.rousing.service;

import com.rousing.mapper.HistoryMapper;
import com.rousing.pojo.Collect;
import com.rousing.pojo.Comment;
import com.rousing.pojo.Like;
import com.rousing.pojo.View;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class HistoryService {
    @Resource
    HistoryMapper historyMapper;

    public List<Like> selectLikeHistory(Like like) {
        return historyMapper.selectLikeHistory(like);
    }

    public List<Comment> selectCommentHistory(Comment comment) {
        return historyMapper.selectCommentHistory(comment);
    }

    public List<Collect> selectCollectHistory(Collect collect) {
        return historyMapper.selectCollectHistory(collect);
    }

    public List<View> selectViewHistory(View view) {
        return historyMapper.selectViewHistory(view);
    }
}
