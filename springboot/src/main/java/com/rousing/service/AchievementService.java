package com.rousing.service;

import com.rousing.mapper.ArticleAchievementMapper;
import com.rousing.mapper.UserAchievementMapper;
import com.rousing.pojo.ArticleAchievement;
import com.rousing.pojo.UserAchievement;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AchievementService {
    @Resource
    UserAchievementMapper userAchievementMapper;
    @Resource
    ArticleAchievementMapper articleAchievementMapper;

    public UserAchievement getUserAchievement(Integer userid) {
        UserAchievement achievement = new UserAchievement();
        achievement.setUserid(userid);
        //粉丝
        achievement.setFans(userAchievementMapper.selectFans(userid));
        //关注
        achievement.setAttention(userAchievementMapper.selectAttention(userid));

        //拥有的帖子
        List<Integer> list = userAchievementMapper.selectArticle(userid);
        //收藏
        achievement.setCollect(userAchievementMapper.selectCollect(list));
        //阅读
        achievement.setView(userAchievementMapper.selectView(list));
        //点赞
        achievement.setLike(userAchievementMapper.selectLike(list));

        return achievement;
    }

    public ArticleAchievement getArticleAchievement(Integer articleId, Integer userid) {
        ArticleAchievement achievement = new ArticleAchievement();
        achievement.setArticleId(articleId);
        //收藏
        achievement.setCollect(articleAchievementMapper.selectCollect(articleId));
        //阅读
        achievement.setView(articleAchievementMapper.selectView(articleId));
        //点赞
        achievement.setLike(articleAchievementMapper.selectLike(articleId));
        //评论
        achievement.setComment(articleAchievementMapper.selectComment(articleId));
        //用户是否点赞了这篇帖子
        achievement.setLike_it(articleAchievementMapper.selectUserLikeIt(articleId, userid) > 0);
        achievement.setComment_it(articleAchievementMapper.selectUserCommentIt(articleId, userid) > 0);
        achievement.setCollect_it(articleAchievementMapper.selectUserCollectIt(articleId, userid) > 0);
        return achievement;
    }

    public Boolean likeChange(boolean type, Integer artId, Integer userid) {
        if (type) {
            //取消
            return articleAchievementMapper.userUnlikeIt(artId, userid) > 0;
        } else {
            //判断是否曾经点赞过
            if (articleAchievementMapper.userLikeIt(artId, userid) > 0) {
                return true;
            } else {
                return articleAchievementMapper.newUserLikeIt(artId, userid) > 0;
            }
        }
    }

    public Boolean collectChange(boolean type, Integer artId, Integer userid) {
        if (type) {
            //取消
            return articleAchievementMapper.userUnCollectIt(artId, userid) > 0;
        } else {
            if (articleAchievementMapper.userCollectIt(artId, userid) > 0) {
                return true;
            } else {
                return articleAchievementMapper.newUserCollectIt(artId, userid) > 0;
            }
        }
    }

}
