package com.rousing.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    private Integer comId;
    private User comUser;
    private String comContext;
    private Article comArticle;
    private Date comCreate;
    private Integer comStatus;
}
