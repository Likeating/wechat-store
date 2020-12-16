package com.fortwelve.wechatstore.controller;

import com.auth0.jwt.interfaces.Claim;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fortwelve.wechatstore.dto.Code2Session;
import com.fortwelve.wechatstore.dto.UserInfo;
import com.fortwelve.wechatstore.pojo.Customer;
import com.fortwelve.wechatstore.service.CustomerService;
import com.fortwelve.wechatstore.util.JWTUtils;
import com.fortwelve.wechatstore.util.WXapi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    JWTUtils jwtUtils;
    @Autowired
    WXapi wxapi;
    @Autowired
    CustomerService customerService;
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @PostMapping("/login")
    public Object login(String code, @RequestBody String code2, HttpServletResponse response){
        Map<String,Object> map = new HashMap<>();
        Map<String,Object> msg = new HashMap<>();
        Map<String,Object> meta = new HashMap<>();

        Map<String,String> tokenMap = new HashMap<>();
        Customer customer;

        try{
            Code2Session code2Session = wxapi.Code2Session(code);
            System.out.println("code:"+code);
            System.out.println("code2:"+code2);
            if(code2Session.getErrcode()==0){
                customer = customerService.getCustomerByOpenId(code2Session.getOpenid());
                if(null == customer){//未注册
                    //保存到redis中
                    stringRedisTemplate.opsForHash().put(code,"openid",code2Session.getOpenid());
                    stringRedisTemplate.opsForHash().put(code,"session_key",code2Session.getSession_key());
                    stringRedisTemplate.expire(code,30,TimeUnit.MINUTES);

                    //返回包含了code的token，以便于注册用户功能
                    tokenMap.put("code",code);
                    response.setHeader("token",jwtUtils.getToken(tokenMap));

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

        UserInfo userInfo = customerService.CustomerToUserInfo(customer);

        tokenMap.put("userId", String.valueOf(userInfo.getUserId()));
        tokenMap.put("nickName", userInfo.getNickName());

        response.setHeader("token",jwtUtils.getToken(tokenMap));

        msg.put("userInfo",userInfo);
        meta.put("msg","登录成功");
        meta.put("status",200);
        map.put("meta",meta);
        map.put("message",msg);
        return map;
    }

    @PostMapping("/register")
    public Object register(String rawData, String signature, String encryptedData, String iv, HttpServletRequest request,HttpServletResponse response){
        Map<String,Object> map = new HashMap<>();
        Map<String,Object> msg = new HashMap<>();
        Map<String,Object> meta = new HashMap<>();
        Map<String,String> tokenMap = new HashMap<>();

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            System.out.println("rawData:"+rawData);
            System.out.println("signature:"+signature);
            System.out.println("encryptedData:"+encryptedData);
            System.out.println("iv:"+iv);

            String token = request.getHeader("token");
            if (null == token || token.equals("")){
                meta.put("msg","token无效，请重新注册。");
                meta.put("status",601);
                map.put("meta",meta);
                return map;
            }
            Map<String, Claim> claimMap = jwtUtils.decode(token);
            String code = claimMap.get("code").asString();

            HashOperations<String,String,String> ops = stringRedisTemplate.opsForHash();

            String openid = ops.get(code,"openid");
            String session_key = ops.get(code,"session_key");

            //数据校验
            if (!wxapi.getSHA1(rawData+session_key).equals(signature)){
                meta.put("msg","签名不一致或信息遭到篡改。");
                meta.put("status",605);
                map.put("meta",meta);
                return map;
            }
            //校验通过，注册
            UserInfo userInfo = objectMapper.readValue(rawData,UserInfo.class);
            //转换成customer
            Customer customer = customerService.UserInfoToCustomer(userInfo,openid);
            //存入数据库
            customerService.addCustomer(customer);
            //返回自增加主键值
            userInfo.setUserId(customer.getCustomer_id());

            tokenMap.put("userId", String.valueOf(userInfo.getUserId()));
            tokenMap.put("nickName", userInfo.getNickName());

            response.setHeader("token",jwtUtils.getToken(tokenMap));

            msg.put("userInfo",userInfo);
            meta.put("msg","登录成功");
            meta.put("status",200);
            map.put("meta",meta);
            map.put("message",msg);
        }catch (Exception e){
            e.printStackTrace();
            meta.put("msg","服务器出错。");
            meta.put("status",500);
            map.put("meta",meta);
        }
        return map;
    }
}
