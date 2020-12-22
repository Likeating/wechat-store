package com.fortwelve.wechatstore.service;

import com.fortwelve.wechatstore.pojo.Category;

import java.util.List;

public interface CategoryService {
    int addCategory(Category category);
    int deleteCategoryById(int id);
    int updateCategory(Category category);
    Category getCategoryById(int id);
    List<Category> getAllCategory();

}
