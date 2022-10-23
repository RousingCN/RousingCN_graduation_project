package com.rousing.config;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginHandleInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 判断发起请求的用户是否处于登陆状态
        Object user = request.getSession().getAttribute("user");
        // 如果找不到用户信息，则拒绝请求
        if (user == null) {
            System.out.println("未知来源请求");
            response.encodeRedirectURL("http://localhost:9876/");
            return false;
        } else {
            return true;
        }

    }
}
