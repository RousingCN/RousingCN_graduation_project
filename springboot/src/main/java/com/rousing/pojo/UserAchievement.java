package com.rousing.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAchievement {
    private Integer userid;
    private Integer fans;
    private Integer attention;
    private Integer collect;
    private Integer like;
    private Integer view;
}
