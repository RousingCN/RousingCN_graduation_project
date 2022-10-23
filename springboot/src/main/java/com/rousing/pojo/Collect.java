package com.rousing.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Collect {
    private Integer colId;
    private Article colArticle;
    private User colUser;
    private Integer colStatus;
}
