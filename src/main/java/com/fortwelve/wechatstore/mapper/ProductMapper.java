package com.fortwelve.wechatstore.mapper;

import com.fortwelve.wechatstore.pojo.Product;
import org.apache.ibatis.annotations.*;

import java.math.BigInteger;
import java.util.List;

@Mapper
public interface ProductMapper {

    @Insert("insert into product (product_name,price,category_id,add_time,delete_time,state,picture_id) values (#{product_name},#{price},#{category_id},#{add_time},#{delete_time},#{state},#{picture_id})")
    @Options(useGeneratedKeys = true,keyProperty = "product_id",keyColumn = "product_id")
    public int addProduct(Product product);

    @Select("select * from product where product_id = #{id}")
    public Product getProductById(BigInteger id);

    @Select("select * from product")
    public List<Product> getAllProduct();

    @Select("select * from product limit #{offset},#{rows}")
    public List<Product> getProductPage(int offset,int rows);

    @Delete("delete from product where product_id=#{id}")
    public int deleteProductById(BigInteger id);

    @Update("update product set product_name=#{product_name},price=#{price},category_id=#{category_id},add_time=#{add_time},delete_time=#{delete_time},state=#{state},picture_id=#{picture_id} where product_id=#{product_id}")
    public int updateProduct(Product product);

}
