package com.rousing.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Integer userid;
    private String username;
    private String userPassword;
    private String userPhone;
    private String userMail;
    private String userSex;
    private Date userBirth;
    private Date userCreate;
    private String userAvatar;
    private String userinfo;
    private Integer userStatus;
}
