package com.fortwelve.wechatstore.controller;

import com.fortwelve.wechatstore.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/category")
public class CategoryControllers {
    @Autowired
    CategoryService categoryService;
    @PostMapping("/addCategory")
    public Object addCategory(){
        Map<String,Object> map = new HashMap<>();
        Map<String,Object> meta = new HashMap<>();
        Map<String,Object> msg = new HashMap<>();
        return map;
    }
}
