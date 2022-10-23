package com.rousing.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Like {
    private Integer likeId;
    private Article likeArticle;
    private User likeUser;
    private Integer likeStatus;
    private Integer startIndex;
    private Integer endIndex;
}
