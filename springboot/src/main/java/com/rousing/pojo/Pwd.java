package com.rousing.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pwd {
    private String oldPassword;
    private String newPassword;
    private Integer userid;
}
