package com.fortwelve.wechatstore.service;

import com.fortwelve.wechatstore.pojo.Category;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CategoryService {
    int addCategory(Category category);
    int deleteCategoryById(int id);
    int updateCategory(Category category);
    Category getCategoryById(int id);
    List<Category> getAllCategory();

    List<Category> getCategoryPage(@Param("offset") int offset, @Param("rows") int rows);

}
