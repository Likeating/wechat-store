package com.fortwelve.wechatstore.dao;

import com.fortwelve.wechatstore.pojo.Category;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CategoryMapper {

    @Insert("insert into category (category_name,picture_id) values (#{category_name},#{picture_id})")
    @Options(useGeneratedKeys = true,keyProperty = "category_id",keyColumn = "category_id")
    public int addCategory(Category category);

    @Delete("delete from category where category_id=#{id}")
    public int deleteCategoryById(int id);

    @Update("update category set category_name=#{category_name},picture_id=#{picture_id} where category_id=#{category_id}")
    public int updateCategory(Category category);

    @Select("select * from category where category_id=#{id}")
    public Category getCategoryById(int id);

    @Select("select * from category")
    public List<Category> getAllCategory();

}
