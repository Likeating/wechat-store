package com.fortwelve.wechatstore.controller;

import com.fortwelve.wechatstore.component.MsgMap;
import com.fortwelve.wechatstore.dto.CategoryDTO;
import com.fortwelve.wechatstore.pojo.Category;
import com.fortwelve.wechatstore.pojo.Picture;
import com.fortwelve.wechatstore.service.CategoryService;
import com.fortwelve.wechatstore.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/category")
public class CategoryControllers {
    @Autowired
    CategoryService categoryService;

    @Autowired
    PictureService pictureService;

    @PostMapping("/addCategory")
    public Object addCategory(CategoryDTO categoryDTO, HttpServletResponse response){
        MsgMap msg = new MsgMap();

        try{
            if(categoryDTO.getCategory_name()==null){
                msg.setMeta("请求不正确。",701);
                return msg;
            }

            Category category=new Category();
            //category.setCategory_id(categoryDTO.getCategory_id());
            category.setCategory_name(categoryDTO.getCategory_name());
            category.setPicture_id(categoryDTO.getPicture_id());

            if(categoryService.addCategory(category)==0){
                msg.setMeta("服务器出错。",500);
                return msg;
            }

            msg.put("category_id",category.getCategory_id());
            msg.put("category_name",categoryDTO.getCategory_name());
            msg.put("picture",pictureService.getPictureById(categoryDTO.getPicture_id()).getUrl());

            msg.setMeta("操作成功。",200);
        }catch (Exception e){
            msg.setMeta("服务器出错。",500);
        }

        return msg;
    }

    @PostMapping("updateCategory")
    public Object updateCategory(CategoryDTO categoryDTO, HttpServletResponse response){
        MsgMap msg = new MsgMap();
        try{
            if(categoryDTO.getCategory_name()==null){
                msg.setMeta("请求不正确。",701);
                return msg;
            }

            Category category=new Category();
            category.setCategory_id(categoryDTO.getCategory_id());
            category.setCategory_name(categoryDTO.getCategory_name());
            category.setPicture_id(categoryDTO.getPicture_id());
            if(categoryService.updateCategory(category)==0){
                msg.setMeta("服务器出错。",500);
                return msg;
            }

            msg.put("category_id",categoryDTO.getCategory_id());
            msg.put("category_name",categoryDTO.getCategory_name());
            msg.put("picture",pictureService.getPictureById(categoryDTO.getPicture_id()).getUrl());

            msg.setMeta("操作成功",200);
        }catch (Exception e){
            msg.setMeta("服务器出错。",500);
        }

        return msg;
    }

    @RequestMapping("getCategory")
    public Object getCategory(Integer currentPage,Integer pageSize){
        Map<String,Object> map;
        List<Object> list = new LinkedList<>();
        MsgMap msg = new MsgMap();
        try{

            List<Category> categoryList;

            if(pageSize==null){
                categoryList=categoryService.getAllCategory();
            }else {
                int current;
                if(currentPage==null || currentPage<0){
                    current=1;
                }else{
                    current=currentPage;
                }
                int head=(pageSize*(current-1));
                categoryList=categoryService.getCategoryPage(head,pageSize);
                msg.put("currentPage",current);
            }
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

            msg.put("total",categoryService.getAllCategory().size());
            msg.put("list",list);

            msg.setMeta("操作成功。",200);
        }catch (Exception e){
            msg.setMeta("服务器出错。",200);
        }
        return msg;
    }

    @PostMapping("/deleteCategory")
    public Object deleteCategory(int id,HttpServletResponse response){
        MsgMap msg = new MsgMap();
        try{
            if(categoryService.deleteCategoryById(id)==0){
                msg.setMeta("删除失败。",500);
                return msg;
            }
            msg.setMeta("操作成功",200);
            //msg.put("操作成功",200);
        }catch (Exception e){
            msg.setMeta("服务器出错。",500);
        }

        return msg;
    }
}