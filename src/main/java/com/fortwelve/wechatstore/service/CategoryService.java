package com.fortwelve.wechatstore.service;

import com.fortwelve.wechatstore.dao.CategoryMapper;
import com.fortwelve.wechatstore.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    CategoryMapper categoryMapper;

    public int addCategory(Category category){
        return categoryMapper.addCategory(category);
    }
    public int deleteCategoryById(int id){
        return categoryMapper.deleteCategoryById(id);
    }
    public int updateCategory(Category category){
        return categoryMapper.updateCategory(category);
    }
    public Category getCategoryById(int id){
        return categoryMapper.getCategoryById(id);
    }
    public List<Category> getAllCategory(){
        return categoryMapper.getAllCategory();
    }

}
