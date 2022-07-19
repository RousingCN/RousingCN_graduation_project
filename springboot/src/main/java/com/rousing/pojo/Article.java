package com.rousing.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article {
    private Integer artId;
    private String artTitle;
    private String artContext;
    private Integer artModule;
    private User artAuthor;
    private Date artCreate;
    private Integer artStatus;
}
