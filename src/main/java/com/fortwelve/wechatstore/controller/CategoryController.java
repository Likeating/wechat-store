package com.fortwelve.wechatstore.controller;

import com.fortwelve.wechatstore.component.MsgMap;
import com.fortwelve.wechatstore.pojo.Category;
import com.fortwelve.wechatstore.pojo.Picture;
import com.fortwelve.wechatstore.service.CategoryService;
import com.fortwelve.wechatstore.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Null;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestController
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @Autowired
    PictureService pictureService;

    @RequestMapping(value = "/categories",produces = "application/json;charset=utf-8")
    public Object categories(){
        MsgMap msg = new MsgMap();

        Map<String,Object> map;
        List<Object> list = new LinkedList<>();


        try{
            List<Category> categoryList=categoryService.getAllCategory();
            for(int i=0;i<categoryList.size();i++) {
                BigInteger id =categoryList.get(i).getPicture_id();
                Picture picture = new Picture();
                picture = pictureService.getPictureById(id);
                map = new HashMap<>();
                map.put("id",categoryList.get(i).getCategory_id());
                map.put("name",categoryList.get(i).getCategory_name());
                map.put("picture",picture.getUrl());
                list.add(map);
            }
            msg.put("catagories",list);
            msg.setMeta("获取成功。",200);
        }catch (Exception e){
            System.out.println(e);
            msg.setMeta("服务器出错。",200);
        }
        return msg;
    }
}