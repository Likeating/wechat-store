package com.fortwelve.wechatstore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fortwelve.wechatstore.dao.CustomerMapper;
import com.fortwelve.wechatstore.dto.Code2Session;
import com.fortwelve.wechatstore.dto.UserInfo;
import com.fortwelve.wechatstore.pojo.Customer;
import com.fortwelve.wechatstore.service.CustomerService;
import com.fortwelve.wechatstore.util.JWTUtils;
import com.fortwelve.wechatstore.util.WXapi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    JWTUtils jwtUtils;
    @Autowired
    WXapi wxapi;

    @Autowired
    CustomerService customerService;

    @RequestMapping("/login")
    public Object login(String code,HttpServletResponse response){
        Map<String,Object> map = new HashMap<>();
        Map<String,Object> msg = new HashMap<>();
        Map<String,Object> meta = new HashMap<>();

        Customer customer;

        try{
            Code2Session code2Session = wxapi.Code2Session(code);
            if(code2Session.getErrcode()==0){
                customer = customerService.getCustomerByOpenId(code2Session.getOpenid());
                if(null == customer){//未注册
                    meta.put("msg","未注册");
                    meta.put("status",604);
                    map.put("meta",meta);
                    return map;
                }
            }else {
                meta.put("msg","登录失败！"+code2Session.getErrmsg());
                meta.put("status",code2Session.getErrcode());
                map.put("meta",meta);
                return map;
            }

        }catch (IOException e){
            e.printStackTrace();
            meta.put("msg","服务器出错。");
            meta.put("status",500);
            return map;
        }
        //登录成功，且已经注册的用户
        Map<String,String> tokenMap = new HashMap<>();

        UserInfo userInfo = customerService.getUserInfo(customer);

        tokenMap.put("userId", String.valueOf(userInfo.getUserId()));
        tokenMap.put("nickName", String.valueOf(userInfo.getUserId()));

        response.setHeader("token",jwtUtils.getToken(tokenMap));

        msg.put("userInfo",userInfo);
        meta.put("msg","登录成功");
        meta.put("status",200);
        map.put("meta",meta);
        map.put("message",msg);
        return map;
    }


    @RequestMapping("/register")
    public Object register(String rawData,String signature,String encryptedData,String iv,HttpServletResponse response){
        Map<String,Object> map = new HashMap<>();
        Map<String,Object> msg = new HashMap<>();
        Map<String,Object> meta = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            UserInfo userInfo = objectMapper.readValue(rawData,UserInfo.class);

        }catch (Exception e){
            e.printStackTrace();
            meta.put("msg","服务器出错。");
            meta.put("status",500);
            return map;
        }
        System.out.println(rawData);
        System.out.println(signature);
        return map;
    }
}
