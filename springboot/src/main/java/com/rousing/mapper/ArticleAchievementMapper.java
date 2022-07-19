package com.rousing.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ArticleAchievementMapper {
    Integer selectCollect(@Param("artId") Integer artId);
    Integer selectView(@Param("artId") Integer artId);
    Integer selectLike(@Param("artId") Integer artId);
}
