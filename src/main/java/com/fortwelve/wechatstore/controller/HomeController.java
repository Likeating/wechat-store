package com.fortwelve.wechatstore.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/home")
public class HomeController {

    @RequestMapping("/swiperdata")
    public Object swiperdata(){
        Map<String,Object> map = new HashMap<>();
        Map<String,Object> msgbody = new HashMap<>();
        Map<String,Object> meta = new HashMap<>();
        List<Object> msg = new ArrayList<>();

        System.out.println("接受");
        msgbody.put("image_src","https://api-hmugo-web.itheima.net/pyg/banner1.png");
        msgbody.put("open_type","navigate");
        msgbody.put("goods_id",129);
        msgbody.put("navigator_url","/pages/goods_detail/index?goods_id=129");
        msg.add(msgbody);

        meta.put("msg","获取成功");
        meta.put("status",200);

        map.put("message",msg);
        map.put("meta",meta);

        return map;
    }
}
