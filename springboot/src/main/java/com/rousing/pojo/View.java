package com.rousing.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class View {
    private Integer viewId;
    private Article viewArticle;
    private User viewUser;
    private Date viewTime;
}
