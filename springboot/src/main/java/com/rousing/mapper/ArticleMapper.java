package com.rousing.mapper;

import com.rousing.pojo.Article;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ArticleMapper {
    List<Article> selectArticleList(Map<String, Integer> map);

    int selectAllCount();
    int insertArticle(Article article);
}
