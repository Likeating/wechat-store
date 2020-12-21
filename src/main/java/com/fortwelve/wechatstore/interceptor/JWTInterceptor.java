package com.fortwelve.wechatstore.interceptor;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fortwelve.wechatstore.util.JWTUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


public class JWTInterceptor implements HandlerInterceptor {

    private String signature;
    private int minute;

    public JWTInterceptor(String signature, int minute) {
        this.signature = signature;
        this.minute = minute;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");

        Map<String,Object> map = new HashMap<>();
        Map<String,Object> meta = new HashMap<>();

        try {
            if(null != token){
                JWTUtils.verify(token,signature);
                return true;
            }
            meta.put("msg","token无效，请重新登录。");
            meta.put("status",601);
        }catch (SignatureVerificationException e){
            meta.put("msg","token无效，请重新登录。");
            meta.put("status",601);

        }catch (TokenExpiredException e){
            meta.put("msg","token过期，请重新登录。");
            meta.put("status",602);

        }catch (AlgorithmMismatchException e){
            meta.put("msg","token算法不一致！");
            meta.put("status",603);

        }catch (Exception e){
            meta.put("msg","未知错误！");
            meta.put("status",500);
//            e.printStackTrace();
        }
//        return true;//暂时改为true
        map.put("meta",meta);
        ObjectMapper objectMapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().append(objectMapper.writeValueAsString(map));
        return false;
    }
}
