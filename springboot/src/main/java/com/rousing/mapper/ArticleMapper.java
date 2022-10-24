package com.rousing.mapper;

import com.rousing.pojo.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface ArticleMapper {
    List<Article> selectArticleList(Map<String, Integer> map);

    int selectAllCount(@Param("moduleId") Integer moduleId);

    int insertArticle(Article article);
}
