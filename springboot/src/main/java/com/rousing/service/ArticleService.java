package com.rousing.service;

import com.rousing.mapper.ArticleMapper;
import com.rousing.pojo.Article;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ArticleService {
    @Resource
    ArticleMapper articleMapper;

    public List<Article> getPageArticle(Integer moduleId, Integer pageNum) {
        Map<String, Integer> map = new HashMap<String, Integer>() {{
            put("moduleId", moduleId);
            put("startNum", (pageNum - 1) * 10);
            put("endNum", pageNum * 10);
        }};
        return articleMapper.selectArticleList(map);
    }

    public int getAllCount(Integer moduleId) {
        return articleMapper.selectAllCount(moduleId);
    }

    public boolean addArticle(Article article) {
        return articleMapper.insertArticle(article) >= 1;
    }
}
