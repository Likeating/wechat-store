package com.fortwelve.wechatstore.config;

import com.fortwelve.wechatstore.interceptor.JWTInterceptor;
import com.fortwelve.wechatstore.util.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private JWTUtils jwtUtils;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JWTInterceptor(jwtUtils))
                .addPathPatterns("/**")
                .excludePathPatterns("/user/**")
                .excludePathPatterns("/home/**");
    }
}
