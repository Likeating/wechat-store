package com.fortwelve.wechatstore.controller;


import com.fortwelve.wechatstore.component.MsgMap;
import com.fortwelve.wechatstore.dto.ProductPropertiesDTO;
import com.fortwelve.wechatstore.pojo.Product;
import com.fortwelve.wechatstore.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.math.BigInteger;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/goods/")
public class GoodsController {

    @Autowired
    ProductService productService;

    @RequestMapping("/search")
    public Object search(String query, Integer cid, Integer sort,Integer pagenum,Integer pagesize){
        MsgMap msg = new MsgMap();

        try{
            String str =null;
            List<String> keywords=null;
            if(query!=null && ! (str = query.trim()).equals("")){//有关键字
                System.out.println(str);
                keywords =Arrays.asList(str.split("\\s+"));
            }
            Integer start = null;
            if(null != pagenum){
                if(pagenum < 1){
                    pagenum = 1;
                }
                start = (pagenum - 1)*5;
            }else {
                pagenum = 1;
            }
            List<Product> productList = productService.searchProductPage(keywords,cid,sort,start,pagesize);
            List<ProductPropertiesDTO> productPropertiesList = new LinkedList<>();
            for(Product tmp : productList){
                productPropertiesList.add(productService.getProductProperties(tmp));
            }
            int totalNum = productService.searchProductPage(keywords,cid,sort,null,null).size();
            msg.setMeta("获取成功。",200);
            msg.put("pagenum",pagenum);
            msg.put("total",totalNum);
            msg.put("product",productPropertiesList);

        }catch (Exception e){
            e.printStackTrace();
            log.info("/goods/search出错："+e.getMessage());
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
            e.printStackTrace();
            log.info("/goods/detail出错："+e.getMessage());
            msg.setMeta("服务器出错。",500);
        }
        return msg;
    }


    @RequestMapping("/addGoods")
    public Object addGoods(Integer goods_id) {
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
