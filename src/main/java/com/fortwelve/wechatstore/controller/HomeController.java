package com.fortwelve.wechatstore.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;


@RestController
@RequestMapping("/home")
public class HomeController {

    @RequestMapping("/swiperdata")
    public Object swiperdata(){
        Map<String,Object> map = new HashMap<>();
        Map<String,Object> msgbody = new HashMap<>();
        Map<String,Object> meta = new HashMap<>();
        List<Object> msg = new ArrayList<>();

        msgbody.put("image_src","https://api-hmugo-web.itheima.net/pyg/banner1.png");
        msgbody.put("open_type","navigate");
        msgbody.put("goods_id",129);
        msgbody.put("navigator_url","/pages/goods_detail/index?goods_id=129");
        msg.add(msgbody);

        msgbody.put("image_src","https://api-hmugo-web.itheima.net/pyg/banner2.png");
        msgbody.put("open_type","navigate");
        msgbody.put("goods_id",130);
        msgbody.put("navigator_url","/pages/goods_detail/index?goods_id=130");
        msg.add(msgbody);

        msgbody.put("image_src","https://api-hmugo-web.itheima.net/pyg/banner3.png");
        msgbody.put("open_type","navigate");
        msgbody.put("goods_id",131);
        msgbody.put("navigator_url","/pages/goods_detail/index?goods_id=131");
        msg.add(msgbody);

        meta.put("msg","获取成功");
        meta.put("status",200);

        map.put("msg",msg);
        map.put("meta",meta);

        return map;
    }
    @RequestMapping("/floordata")
    public Object floordata(){
        Map<String,Object> map = new HashMap<>();
        List<Object> message = new LinkedList();
        Map<String,Object> tmp = new HashMap<>();
        Map<String,Object> meta = new HashMap<>();
        Map<String,Object> floor_title = new HashMap<>();
        List<Object> product_list = new LinkedList();
        Map<String,Object> product1 = new HashMap<>();
        Map<String,Object> product2 = new HashMap<>();
        Map<String,Object> product3 = new HashMap<>();
        Map<String,Object> product4 = new HashMap<>();
        Map<String,Object> product5 = new HashMap<>();

        floor_title.put("name","时尚女装");
        floor_title.put("image_src","https://api-hmugo-web.itheima.net/pyg/pic_floor01_title.png");
        tmp.put("floor_title",floor_title);

        product1.put("name","优质服饰");
        product1.put("image_src","https://api-hmugo-web.itheima.net/pyg/pic_floor01_1@2x.png");
        product1.put("image_width",232);
        product1.put("open_type","navigate");
        product1.put("navigator_url","/pages/goods_list/index?query=服饰");

        product2.put("name","春季热门");
        product2.put("image_src","https://api-hmugo-web.itheima.net/pyg/pic_floor01_2@2x.png");
        product2.put("image_width",232);
        product2.put("open_type","navigate");
        product2.put("navigator_url","/pages/goods_list/index?query=热");

        product3.put("name","爆款清仓");
        product3.put("image_src","https://api-hmugo-web.itheima.net/pyg/pic_floor01_3@2x.png");
        product3.put("image_width",233);
        product3.put("open_type","navigate");
        product3.put("navigator_url","/pages/goods_list/index?query=爆款");

        product4.put("name","倒春寒");
        product4.put("image_src","https://api-hmugo-web.itheima.net/pyg/pic_floor01_4@2x.png");
        product4.put("image_width",233);
        product4.put("open_type","navigate");
        product4.put("navigator_url","/pages/goods_list/index?query=春季");

        product5.put("name","怦然心动");
        product5.put("image_src","https://api-hmugo-web.itheima.net/pyg/pic_floor01_5@2x.png");
        product5.put("image_width",233);
        product5.put("open_type","navigate");
        product5.put("navigator_url","/pages/goods_list/index?query=心动");

        product_list.add(product1);
        product_list.add(product2);
        product_list.add(product3);
        product_list.add(product4);
        product_list.add(product5);
        tmp.put("product_list",product_list);

        message.add(tmp);

        Map<String,Object> tmp2 = new HashMap<>();
        Map<String,Object> floor_title2 = new HashMap<>();
        List<Object> product_list2 = new LinkedList();
        Map<String,Object> product21 = new HashMap<>();
        Map<String,Object> product22 = new HashMap<>();
        Map<String,Object> product23 = new HashMap<>();
        Map<String,Object> product24 = new HashMap<>();
        Map<String,Object> product25 = new HashMap<>();
        floor_title2.put("name","户外活动");
        floor_title2.put("image_src","https://api-hmugo-web.itheima.net/pyg/pic_floor02_title.png");
        tmp2.put("floor_title",floor_title2);

        product21.put("name","勇往直前");
        product21.put("image_src","https://api-hmugo-web.itheima.net/pyg/pic_floor02_1@2x.png");
        product21.put("image_width",232);
        product21.put("open_type","navigate");
        product21.put("navigator_url","/pages/goods_list/index?query=户外");

        product22.put("name","户外登山包");
        product22.put("image_src","https://api-hmugo-web.itheima.net/pyg/pic_floor02_2@2x.png");
        product22.put("image_width",273);
        product22.put("open_type","navigate");
        product22.put("navigator_url","/pages/goods_list/index?query=登山包");

        product23.put("name","超强手套");
        product23.put("image_src","https://api-hmugo-web.itheima.net/pyg/pic_floor02_3@2x.png");
        product23.put("image_width",193);
        product23.put("open_type","navigate");
        product23.put("navigator_url","/pages/goods_list/index?query=手套");

        product24.put("name","户外运动鞋");
        product24.put("image_src","https://api-hmugo-web.itheima.net/pyg/pic_floor02_4@2x.png");
        product24.put("image_width",193);
        product24.put("open_type","navigate");
        product24.put("navigator_url","/pages/goods_list/index?query=运动鞋");

        product25.put("name","冲锋衣系列");
        product25.put("image_src","https://api-hmugo-web.itheima.net/pyg/pic_floor02_5@2x.png");
        product25.put("image_width",273);
        product25.put("open_type","navigate");
        product25.put("navigator_url","/pages/goods_list/index?query=冲锋衣");

        product_list2.add(product21);
        product_list2.add(product22);
        product_list2.add(product23);
        product_list2.add(product24);
        product_list2.add(product25);
        tmp2.put("product_list",product_list2);

        message.add(tmp2);

        Map<String,Object> tmp3 = new HashMap<>();
        Map<String,Object> floor_title3 = new HashMap<>();
        List<Object> product_list3 = new LinkedList();
        Map<String,Object> product31 = new HashMap<>();
        Map<String,Object> product32 = new HashMap<>();
        Map<String,Object> product33 = new HashMap<>();
        Map<String,Object> product34 = new HashMap<>();
        Map<String,Object> product35 = new HashMap<>();

        floor_title3.put("name","箱包配饰");
        floor_title3.put("image_src","https://api-hmugo-web.itheima.net/pyg/pic_floor03_title.png");
        tmp3.put("floor_title",floor_title3);

        product31.put("name","清新气质");
        product31.put("image_src","https://api-hmugo-web.itheima.net/pyg/pic_floor03_1@2x.png");
        product31.put("image_width",232);
        product31.put("open_type","navigate");
        product31.put("navigator_url","/pages/goods_list/index?query=饰品");

        product32.put("name","复古胸针");
        product32.put("image_src","https://api-hmugo-web.itheima.net/pyg/pic_floor03_2@2x.png");
        product32.put("image_width",263);
        product32.put("open_type","navigate");
        product32.put("navigator_url","/pages/goods_list/index?query=胸针");

        product33.put("name","韩版手链");
        product33.put("image_src","https://api-hmugo-web.itheima.net/pyg/pic_floor03_3@2x.png");
        product33.put("image_width",203);
        product33.put("open_type","navigate");
        product33.put("navigator_url","/pages/goods_list/index?query=手链");

        product34.put("name","水晶项链");
        product34.put("image_src","https://api-hmugo-web.itheima.net/pyg/pic_floor03_4@2x.png");
        product34.put("image_width",193);
        product34.put("open_type","navigate");
        product34.put("navigator_url","/pages/goods_list/index?query=项链");

        product35.put("name","情侣表");
        product35.put("image_src","https://api-hmugo-web.itheima.net/pyg/pic_floor03_5@2x.png");
        product35.put("image_width",273);
        product35.put("open_type","navigate");
        product35.put("navigator_url","/pages/goods_list/index?query=情侣表");

        product_list3.add(product31);
        product_list3.add(product32);
        product_list3.add(product33);
        product_list3.add(product34);
        product_list3.add(product35);
        tmp3.put("product_list",product_list3);

        message.add(tmp3);

        meta.put("msg","获取成功");
        meta.put("status",200);

        map.put("msg",message);
        map.put("meta",meta);
        return map;
    }
}
