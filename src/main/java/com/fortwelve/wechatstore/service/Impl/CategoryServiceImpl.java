package com.fortwelve.wechatstore.service.Impl;

import com.fortwelve.wechatstore.dao.CategoryMapper;
import com.fortwelve.wechatstore.pojo.Category;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fortwelve.wechatstore.service.CategoryService;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryMapper categoryMapper;

    @Override
    public int addCategory(Category category){
        return categoryMapper.addCategory(category);
    }

    @Override
    public int deleteCategoryById(int id){
        return categoryMapper.deleteCategoryById(id);
    }

    @Override
    public int updateCategory(Category category) {
        return categoryMapper.updateCategory(category);
    }

    @Override
    public Category getCategoryById(int id) {
        return categoryMapper.getCategoryById(id);
    }

    @Override
    public List<Category> getAllCategory() {
        return categoryMapper.getAllCategory();
    }

    @Override
    public List<Category> getCategoryPage(@Param("offset") int offset, @Param("rows") int rows){
        return categoryMapper.getCategoryPage(offset,rows);
    }
}
