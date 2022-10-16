package com.rousing.config;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginHandleInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Object user = request.getSession().getAttribute("user");
        if (user == null) {
            response.encodeRedirectURL("http://localhost:9876/");
            return false;
        } else {
            return true;
        }

    }
}
