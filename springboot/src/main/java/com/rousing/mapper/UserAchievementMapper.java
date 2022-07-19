package com.rousing.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserAchievementMapper {
    Integer selectFans(@Param("authorId") Integer authorId);

    Integer selectAttention(@Param("userid") Integer userid);

    List<Integer> selectArticle(@Param("authorId") Integer authorId);
    Integer selectCollect(@Param("art_id_list") List<Integer> art_id_list);
    Integer selectView(@Param("art_id_list") List<Integer> art_id_list);
    Integer selectLike(@Param("art_id_list") List<Integer> art_id_list);
}
