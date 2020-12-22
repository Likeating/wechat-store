package com.fortwelve.wechatstore.controller;


import com.fortwelve.wechatstore.component.MsgMap;
import com.fortwelve.wechatstore.dto.ProductPropertiesDTO;
import com.fortwelve.wechatstore.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.math.BigInteger;
import java.util.*;

@RestController
@RequestMapping("/goods/")
public class GoodsController {

    @Autowired
    ProductService productService;

    @RequestMapping("/search")
    public Object search(String query, String cid, @RequestParam int pagenum,@RequestParam int pagesize){
        MsgMap msg = new MsgMap();

        try{
            String str = query.trim();
            if(str==null || str.equals("")){
                throw new Exception();
            }
            List<String> keywords =Arrays.asList(str.split("\\s+"));
            List<ProductPropertiesDTO> productPropertiesList = productService.searchProductPage(keywords,pagenum,pagesize);

            msg.setMeta("获取成功。",200);
            msg.put("pagenum",pagenum);
            msg.put("total",productPropertiesList.size());
            msg.put("product",productPropertiesList);

        }catch (Exception e){
            msg.setMeta("服务器出错。",500);
        }
        return msg;
    }
    @RequestMapping("/detail")
    public Object detail(Integer goods_id) {
        MsgMap msg = new MsgMap();
        try {
            msg.put("productDetail",productService.getProductDetail(BigInteger.valueOf(goods_id)));
            msg.setMeta("获取成功。",200);
        }catch (Exception e){
            msg.setMeta("服务器出错。",500);
        }
        return msg;
    }
}
