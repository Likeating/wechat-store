package com.fortwelve.wechatstore.config;

import com.fortwelve.wechatstore.interceptor.JWTInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${Picture.path}")
    private String picturePath;
    @Value("${Picture.pattern}")
    private String picturePattern;

    @Value("${JWTUtils.wx.signature}")
    private String wxSignature;
    @Value("${JWTUtils.wx.minute}")
    private int wxMinute;

    @Value("${JWTUtils.manager.signature}")
    private String managerSignature;
    @Value("${JWTUtils.manager.minute}")
    private int managerMinute;


    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JWTInterceptor(wxSignature,wxMinute))
                .addPathPatterns("/my/**");

        registry.addInterceptor(new JWTInterceptor(managerSignature,managerMinute))
                .addPathPatterns("/manager/**")
                .excludePathPatterns("/manager/verify")
                .excludePathPatterns("/manager/login")
                .addPathPatterns("/category/**")
                .excludePathPatterns("/category/getCategory");


    }

    //此方法会使Interceptor失效，原因是请求先进入Interceptor
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOriginPatterns("*")
//                .allowCredentials(true)
//                .exposedHeaders("token")
//                .allowedMethods("GET","POST","PUT","DELETE")
//                .maxAge(3600);
//    }
    //改用下面这种或者自己写filter
    @Bean
    public CorsFilter corsFilter(){
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.addAllowedOrigin("*");
        config.addExposedHeader("token");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",config);
        return new CorsFilter(source);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        File img = new File(picturePath);
        if(!img.exists()){
            if(!img.mkdirs()){
                logger.error("创建图片目录出错。");
            }
        }
        logger.info("图片目录："+img.getAbsolutePath());
        registry.addResourceHandler(picturePattern).addResourceLocations("file:"+img.getAbsolutePath().replace("\\","/")+"/");
    }
}
