package com.rousing.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    private Integer comId;
    private Integer comUser;
    private String comContext;
    private Integer comArticle;
    private Date comCreate;
    private Integer comStatus;
//    private List<CommentMulti> comMulti;
}
