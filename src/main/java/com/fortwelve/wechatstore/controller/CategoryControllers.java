package com.fortwelve.wechatstore.controller;

import com.fortwelve.wechatstore.dto.CategoryDTO;
import com.fortwelve.wechatstore.pojo.Category;
import com.fortwelve.wechatstore.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@CrossOrigin
@RestController
@RequestMapping("/category")
public class CategoryControllers {
    @Autowired
    CategoryService categoryService;

    @PostMapping("/addCategory")
    public Object addCategory(CategoryDTO categoryDTO, HttpServletResponse response){
        Map<String,Object> map = new HashMap<>();
        Map<String,Object> meta = new HashMap<>();
        Map<String,Object> msg = new HashMap<>();

        try{
            if(categoryDTO.getCategory_name()==null){
                meta.put("msg","请求不正确。");
                meta.put("status",701);
                map.put("meta",meta);
                return map;
            }

            Category category=new Category();
            category.setCategory_id(categoryDTO.getCategory_id());
            category.setCategory_name(categoryDTO.getCategory_name());

            if(categoryService.addCategory(category)==0){
                meta.put("msg","服务器出错。");
                meta.put("status",500);
                map.put("meta",meta);
                return map;
            }

            msg.put("category_id",categoryDTO.getCategory_id());
            msg.put("category_name",categoryDTO.getCategory_name());

            meta.put("msg","操作成功");
            meta.put("status",200);

            map.put("msg",msg);
            map.put("meta",meta);
        }catch (Exception e){
            meta.put("msg","服务器出错。");
            meta.put("status",500);
            map.put("meta",meta);
        }

        return map;
    }

    @PostMapping("updateCategory")
    public Object updateCategory(CategoryDTO categoryDTO, HttpServletResponse response){
        Map<String,Object> map = new HashMap<>();
        Map<String,Object> meta = new HashMap<>();
        Map<String,Object> msg = new HashMap<>();

        try{
            if(categoryDTO.getCategory_name()==null){
                meta.put("msg","请求不正确。");
                meta.put("status",701);
                map.put("meta",meta);
                return map;
            }

            Category category=new Category();
            category.setCategory_id(categoryDTO.getCategory_id());
            category.setCategory_name(categoryDTO.getCategory_name());
            if(categoryService.updateCategory(category)==0){
                meta.put("msg","服务器出错。");
                meta.put("status",500);
                map.put("meta",meta);
                return map;
            }

            msg.put("category_id",categoryDTO.getCategory_id());
            msg.put("category_name",categoryDTO.getCategory_name());

            meta.put("msg","操作成功");
            meta.put("status",200);

            map.put("msg",msg);
            map.put("meta",meta);
        }catch (Exception e){
            meta.put("msg","服务器出错。");
            meta.put("status",500);
            map.put("meta",meta);
        }

        return map;
    }

    @GetMapping("getCategory")
    public Object getCategory(HttpServletResponse response){
        Map<String,Object> map = new HashMap<>();
        Map<String,Object> meta = new HashMap<>();
        Map<String,Object> msg = new HashMap<>();
        try{

            List<Category> categoryList=categoryService.getAllCategory();

            msg.put("total",categoryList.size());
            msg.put("list",categoryList);

            meta.put("msg","操作成功");
            meta.put("status",200);

            map.put("msg",msg);
            map.put("meta",meta);
        }catch (Exception e){
            meta.put("msg","服务器出错。");
            meta.put("status",500);
            map.put("meta",meta);
        }

        return map;
    }

    @PostMapping("/deleteCategory")
    public Object deleteCategory(int id,HttpServletResponse response){
        Map<String,Object> map = new HashMap<>();
        Map<String,Object> meta = new HashMap<>();
        Map<String,Object> msg = new HashMap<>();

        try{
            if(categoryService.deleteCategoryById(id)==0){
                meta.put("msg","服务器出错。");
                meta.put("status",500);
                map.put("meta",meta);
                return map;
            }
            meta.put("msg","操作成功");
            meta.put("status",200);

            map.put("msg",msg);
            map.put("meta",meta);
        }catch (Exception e){
            meta.put("msg","服务器出错。");
            meta.put("status",500);
            map.put("meta",meta);
        }

        return map;
    }
}
