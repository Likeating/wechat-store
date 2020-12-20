package com.fortwelve.wechatstore.controller;


import com.fortwelve.wechatstore.dto.ProductPropertiesDTO;
import com.fortwelve.wechatstore.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.math.BigInteger;
import java.util.*;

@RestController
@RequestMapping("/goods/")
public class GoodsController {

    @Autowired
    ProductService productService;

    @RequestMapping("/search")
    public Object search(String query,String cid,int pagenum,int pagesize){

        Map<String,Object> map = new HashMap<>();
        Map<String,Object> meta = new HashMap<>();
        Map<String,Object> msg = new HashMap<>();

        try{
            String str = query.trim();
            if(str==null || str.equals("")){
                throw new Exception();
            }
            List<String> keywords =Arrays.asList(str.split("\\s+"));
            List<ProductPropertiesDTO> productPropertiesList = productService.searchProductPage(keywords,pagenum,pagesize);

            meta.put("msg","获取成功");
            meta.put("status",200);

            msg.put("pagenum",pagenum);

            msg.put("total",productPropertiesList.size());
            msg.put("product",productPropertiesList);
            map.put("message",msg);
        }catch (Exception e){
            meta.put("msg","服务器出错。");
            meta.put("status",500);
        }
        map.put("meta",meta);
        return map;
    }
    @RequestMapping("/detail")
    public Object detail(Integer goods_id) {
        Map<String,Object> map = new HashMap<>();
        Map<String,Object> meta = new HashMap<>();
        Map<String,Object> msg = new HashMap<>();

        try {
            msg.put("productDetail",productService.getProductDetail(BigInteger.valueOf(goods_id)));
            meta.put("msg","获取成功");
            meta.put("status",200);
        }catch (Exception e){
            meta.put("msg","服务器出错。");
            meta.put("status",500);
        }
        map.put("meta",meta);
        map.put("msg",msg);
        return map;
    }

}
