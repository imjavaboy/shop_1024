package com.gbq.config;


import com.gbq.interceptor.LoginInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 郭本琪
 * @description
 * @date 2022/11/4 17:54
 * @Copyright 总有一天，会见到成功
 */
@Configuration
@Slf4j
public class InterceptoeConfig implements WebMvcConfigurer {


    public LoginInterceptor loginInterceptor(){
        return new LoginInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor())
                .addPathPatterns("/api/user/*/**","/api/address/*/**")
                .excludePathPatterns("/api/user/*/send_code","/api/user/*/captcha",
                        "/api/user/*/register","/api/user/*/login","/api/user/*/upload");
    }
}
