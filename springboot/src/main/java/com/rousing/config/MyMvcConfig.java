package com.rousing.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class MyMvcConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 拦截器白名单
        List<String> list = new ArrayList<>();
        list.add("/user/login");
        list.add("/user/register");
        list.add("/user/checkUsername");

        // 使用自定义类配置拦截规则，并配置白名单
        registry.addInterceptor(new LoginHandleInterceptor()).addPathPatterns("/**").
                excludePathPatterns(list);
    }

}
