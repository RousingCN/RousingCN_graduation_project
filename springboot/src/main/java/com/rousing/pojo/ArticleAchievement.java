package com.rousing.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleAchievement {
    private Integer articleId;
    private Integer collect;
    private Integer like;
    private Integer view;
}
