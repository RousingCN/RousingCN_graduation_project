package com.rousing.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ArticleAchievementMapper {
    Integer selectCollect(@Param("artId") Integer artId);

    Integer selectView(@Param("artId") Integer artId);

    Integer selectLike(@Param("artId") Integer artId);

    Integer selectComment(@Param("artId") Integer artId);

    Integer selectUserLikeIt(@Param("artId") Integer artId, @Param("userid") Integer userid);

    Integer selectUserCommentIt(@Param("artId") Integer artId, @Param("userid") Integer userid);

    Integer selectUserCollectIt(@Param("artId") Integer artId, @Param("userid") Integer userid);

    Integer userLikeIt(@Param("artId") Integer artId, @Param("userid") Integer userid);

    Integer newUserLikeIt(@Param("artId") Integer artId, @Param("userid") Integer userid);

    Integer userUnlikeIt(@Param("artId") Integer artId, @Param("userid") Integer userid);

    Integer userCollectIt(@Param("artId") Integer artId, @Param("userid") Integer userid);

    Integer newUserCollectIt(@Param("artId") Integer artId, @Param("userid") Integer userid);

    Integer userUnCollectIt(@Param("artId") Integer artId, @Param("userid") Integer userid);

    Integer newUserViewIt(@Param("artId") Integer artId, @Param("userid") Integer userid);

    Integer userViewIt(@Param("artId") Integer artId, @Param("userid") Integer userid);

}
