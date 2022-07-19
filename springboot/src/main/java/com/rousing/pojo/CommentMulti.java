package com.rousing.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentMulti {
    private Integer comMultiId;
    private Integer comMultiUser;
    private String comMultiContext;
    private Date comMultiCreate;
    private Integer comMultiStatus;
}
