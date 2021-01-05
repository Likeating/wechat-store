package com.fortwelve.wechatstore.dao;

import com.fortwelve.wechatstore.pojo.Product;
import org.apache.ibatis.annotations.*;

import java.math.BigInteger;
import java.util.List;

@Mapper
public interface ProductMapper {

    @Insert("insert into product (product_name,price,category_id,add_time,delete_time,state,picture_id,preview_id,detail_id,sale) " +
            "values (#{product_name},#{price},#{category_id},#{add_time},#{delete_time}," +
            "#{state},#{picture_id},#{preview_id},#{detail_id},#{sale})")
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

    @Update("update product set " +
            "product_name=#{product_name},price=#{price},category_id=#{category_id},add_time=#{add_time}," +
            "delete_time=#{delete_time},state=#{state},picture_id=#{picture_id},preview_id=#{preview_id}," +
            "detail_id=#{detail_id},sale=#{sale} " +
            "where product_id=#{product_id}")
    public int updateProduct(Product product);

    @Select("<script>"+
            "select * from product "+
            "<where>"+
                "<if test='keywords!=null'>"+
                    "<foreach item='keyword' collection='keywords' separator=' and '>"+
                    "product_name like concat('%',#{keyword},'%')"+
                    "</foreach>"+
                "</if>"+
                "<if test='cid!=null'> and category_id=#{cid}</if>"+
            "</where>"+
            "<if test='sort!=null'>"+
                "<if test='sort == 1'> order by price desc </if>"+
                "<if test='sort == 2'> order by price asc </if>"+
                "<if test='sort == 3'> order by sale desc </if>"+
                "<if test='sort == 4'> order by sale asc </if>"+
            "</if>"+
            "<if test='rows!=null'> limit <if test='offset!=null'>#{offset},</if>#{rows}</if>"+
            "</script>"
    )
    public List<Product> searchProductPage(List<String> keywords,Integer cid,Integer sort,Integer offset, Integer rows);

}
