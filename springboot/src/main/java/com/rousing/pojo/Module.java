package com.rousing.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Module {
    private Integer moduleId;
    private String moduleName;
    private String moduleInfo;
    private User moduleAuthor;
    private Date moduleCreate;
    private Integer moduleStatus;
}
