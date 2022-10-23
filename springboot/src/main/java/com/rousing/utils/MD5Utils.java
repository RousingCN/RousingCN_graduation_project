package com.rousing.utils;

import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

@Component
public class MD5Utils {
    public static String md5(String str) {
        return DigestUtils.md5DigestAsHex(str.getBytes(StandardCharsets.UTF_8));
    }

    private static final String SALT = "1a4e1d1e";

    /**
     * 第一次加密
     *
     * @param inputPass
     * @return java.lang.String
     * @author LC
     * @operation add
     * @date 4:49 下午 2022/3/1
     **/
    public static String inputPassToFromPass(String inputPass) {
        // salt可以随机的放在输入密码的各个部分当中
        String str = SALT.charAt(0) + SALT.charAt(2) + inputPass + SALT.charAt(5) + SALT.charAt(4);
        return md5(str);
    }

    /**
     * 第二次加密, 使用的盐需要重新生成（这个盐会存放到数据库中，成为用户的一个属性），
     * @author LC
     * @operation add
     * @date 4:52 下午 2022/3/1
     * @param formPass
     * @param salt
     * @return java.lang.String
     **/
    public static String formPassToDBPass(String formPass, String salt) {
        String str = salt.charAt(0) + salt.charAt(2) + formPass + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    /**
     * 将第一次加密和第二次加密调用
     * @param inputPass
     * @param salt
     * @return
     */
    public static String inputPassToDBPass(String inputPass, String salt) {
        String fromPass = inputPassToFromPass(inputPass);
        String dbPass = formPassToDBPass(fromPass, salt);
        return dbPass;
    }

}
