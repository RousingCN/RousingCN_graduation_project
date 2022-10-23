package com.rousing.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Collect {
    private Integer colId;
    private Integer colArticle;
    private Integer colUser;
    private Integer colStatus;
}
