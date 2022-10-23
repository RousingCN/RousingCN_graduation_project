package com.rousing.config;

import org.springframework.boot.system.ApplicationHome;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
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

        // 使用自定义类配置拦截规则，并配置白名单
        registry.addInterceptor(new LoginHandleInterceptor()).addPathPatterns("/**").
                excludePathPatterns(list);
    }


//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        ApplicationHome home = new ApplicationHome(getClass());
//        File file = home.getSource();
//        String dirPath = file.getParentFile().toString() + "/static/avatar/";
//        String os = System.getProperty("os.name");
//        if (os.toLowerCase().startsWith("win")) {
//            registry.addResourceHandler("/static/avatar/**").addResourceLocations("file:" + dirPath);
//        } else {
//            registry.addResourceHandler("/static/avatar/**").addResourceLocations("file:" + dirPath);
//        }
//    }
}
