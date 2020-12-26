package com.fortwelve.wechatstore.controller;

import com.auth0.jwt.interfaces.Claim;
import com.fortwelve.wechatstore.component.MsgMap;
import com.fortwelve.wechatstore.dto.CategoryDTO;
import com.fortwelve.wechatstore.pojo.Category;
import com.fortwelve.wechatstore.pojo.Picture;
import com.fortwelve.wechatstore.service.CategoryService;
import com.fortwelve.wechatstore.service.PictureService;
import com.fortwelve.wechatstore.util.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @Autowired
    PictureService pictureService;

    @Value("${JWTUtils.manager.signature}")
    private String managerSignature;
    @Value("${JWTUtils.manager.minute}")
    private int managerMinute;

    @PostMapping("/addCategory")
    public Object addCategory(@Valid @RequestBody CategoryDTO categoryDTO, BindingResult result, HttpServletRequest request){
        MsgMap msg = new MsgMap();

        try{
            if(result.hasErrors()){
                msg.setMeta(result.getFieldError().getDefaultMessage(),701);
                return msg;
            }
            //权限判断
            String token = request.getHeader("token");
            Map<String, Claim> tokenMap = JWTUtils.decode(token,managerSignature);
            String role = tokenMap.get("role").asString();
            if(!role.equals("超级管理员") && !role.equals("商品管理员")){
                msg.setMeta("没有权限操作。",611);
                return msg;
            }

            Category category=new Category();
            category.setCategory_name(categoryDTO.getCategory_name());
            category.setPicture_id(categoryDTO.getPicture().getPicture_id());

            if(categoryService.addCategory(category)==0){
                msg.setMeta("添加商品分类失败。",641);
                return msg;
            }
            //更新主键
            categoryDTO.setCategory_id(category.getCategory_id());
            //更新图片信息
            categoryDTO.setPicture(pictureService.getPictureById(category.getPicture_id()));
            msg.put("category",categoryDTO);
            msg.setMeta("操作成功。",200);
        }catch (Exception e){
            e.printStackTrace();
            log.info("/category/addCategory出错："+e.getMessage());
            msg.setMeta("服务器出错。",500);
        }

        return msg;
    }

    @PostMapping("/updateCategory")
    public Object updateCategory(@Valid @RequestBody CategoryDTO categoryDTO, BindingResult result, HttpServletRequest request){
        MsgMap msg = new MsgMap();
        try{
            if(result.hasErrors()){
                msg.setMeta(result.getFieldError().getDefaultMessage(),701);
                return msg;
            }
            if(categoryDTO.getCategory_id() == null){
                msg.setMeta("分类ID不能为空。",200);
                return msg;
            }
            //权限判断
            String token = request.getHeader("token");
            Map<String, Claim> tokenMap = JWTUtils.decode(token,managerSignature);
            String role = tokenMap.get("role").asString();
            if(!role.equals("超级管理员") && !role.equals("商品管理员")){
                msg.setMeta("没有权限操作。",611);
                return msg;
            }
            Category category=new Category();
            category.setCategory_id(categoryDTO.getCategory_id());
            category.setCategory_name(categoryDTO.getCategory_name());
            category.setPicture_id(categoryDTO.getPicture().getPicture_id());

            if(categoryService.updateCategory(category)==0){
                msg.setMeta("修改商品分类失败。",642);
                return msg;
            }
            //更新图片信息
            categoryDTO.setPicture(pictureService.getPictureById(category.getPicture_id()));
            msg.put("category",categoryDTO);
            msg.setMeta("操作成功。",200);
        }catch (Exception e){
            e.printStackTrace();
            log.info("/category/updateCategory出错："+e.getMessage());
            msg.setMeta("服务器出错。",500);
        }

        return msg;
    }

    @RequestMapping("/getCategory")
    public Object getCategory(Integer currentPage,Integer pageSize){
        Map<String,Object> map;
        List<CategoryDTO> list = new LinkedList<>();
        MsgMap msg = new MsgMap();
        try{

            List<Category> categoryList;

            if(pageSize==null){
                categoryList=categoryService.getAllCategory();
            }else {
                Integer current;
                if(currentPage==null || currentPage<=0){
                    current=1;
                }else{
                    current=currentPage;
                }
                Integer head=(pageSize*(current-1));
                categoryList=categoryService.getCategoryPage(head,pageSize);
                msg.put("currentPage",current);
            }
            for(int i=0;i<categoryList.size();i++) {
                BigInteger id =categoryList.get(i).getPicture_id();
                Picture picture = pictureService.getPictureById(id);

                CategoryDTO categoryDTO = new CategoryDTO();
                categoryDTO.setCategory_id(categoryList.get(i).getCategory_id());
                categoryDTO.setCategory_name(categoryList.get(i).getCategory_name());
                categoryDTO.setPicture(picture);

                list.add(categoryDTO);
            }

            msg.put("total",categoryService.getAllCategory().size());
            msg.put("list",list);

            msg.setMeta("操作成功。",200);
        }catch (Exception e){
            e.printStackTrace();
            log.info("/category/getCategory出错："+e.getMessage());
            msg.setMeta("服务器出错。",200);
        }
        return msg;
    }

    @RequestMapping("/deleteCategory")
    public Object deleteCategory(@RequestParam int category_id,HttpServletRequest request){
        MsgMap msg = new MsgMap();
        try{
            //权限判断
            String token = request.getHeader("token");
            Map<String, Claim> tokenMap = JWTUtils.decode(token,managerSignature);
            String role = tokenMap.get("role").asString();
            if(!role.equals("超级管理员") && !role.equals("商品管理员")){
                msg.setMeta("没有权限操作。",611);
                return msg;
            }
            if(categoryService.deleteCategoryById(category_id)==0){
                msg.setMeta("删除失败。",500);
                return msg;
            }
            msg.setMeta("操作成功",200);
            //msg.put("操作成功",200);
        }catch (Exception e){
            e.printStackTrace();
            log.info("/category/deleteCategory出错："+e.getMessage());
            msg.setMeta("服务器出错。",500);
        }

        return msg;
    }
}