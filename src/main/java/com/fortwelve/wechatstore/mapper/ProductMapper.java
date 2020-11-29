package com.fortwelve.wechatstore.mapper;

import com.fortwelve.wechatstore.pojo.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ProductMapper {

    @Select("select * from product where product_id = #{id}")
    public Product getProductById(java.math.BigInteger id);

    @Select("select * from product")
    public Product getAllProduct();

    @Select("select * from product limit #{offset},#{rows}")
    public Product getProductPage(int offset,int rows);


}
