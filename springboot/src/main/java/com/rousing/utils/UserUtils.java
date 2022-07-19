package com.rousing.utils;

import com.rousing.pojo.User;

public class UserUtils {
    public static void hideUserInfo(User user) {
        user.setUserPassword("***");
        user.setUserPhone(user.getUserPhone().substring(0, 3) + "******" + user.getUserPhone().substring(9, 11));
        user.setUserMail(user.getUserMail().substring(0, 2) + "***@" + user.getUserMail().split("@")[1]);
    }
}
