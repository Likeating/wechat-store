package com.fortwelve.wechatstore.config;

import com.fortwelve.wechatstore.interceptor.JWTInterceptor;
import com.fortwelve.wechatstore.util.JWTUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
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
                .excludePathPatterns("/manager/login");

    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
//                .allowedOrigins("*")
                .allowedOriginPatterns("*")
                .allowCredentials(true)
                .allowedMethods("GET","POST","PUT","DELETE")
                .maxAge(3600);
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
