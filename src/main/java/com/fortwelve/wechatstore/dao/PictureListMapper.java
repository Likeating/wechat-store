package com.fortwelve.wechatstore.dao;

import com.fortwelve.wechatstore.pojo.PictureList;
import org.apache.ibatis.annotations.*;

import java.math.BigInteger;
import java.util.List;


@Mapper
public interface PictureListMapper {

    @Insert("insert into picture_list (pictures_id) values (#{pictures_id})")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    public int addProductDetail(PictureList pictureList);

    @Delete("delete from picture_list where id=#{id}")
    public int deleteProductDetailById(BigInteger id);

    @Select("select * from picture_list where id = #{id}")
    public PictureList getProductDetailById(BigInteger id);

    @Select("select * from picture_list")
    public List<PictureList> getAllProductDetail();

    @Update("update picture_list set pictures_id=#{pictures_id} where id=#{id}")
    public int updateProductDetail(PictureList pictureList);


}
