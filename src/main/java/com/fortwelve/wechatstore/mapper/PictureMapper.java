package com.fortwelve.wechatstore.mapper;

import com.fortwelve.wechatstore.pojo.Picture;
import org.apache.ibatis.annotations.*;

import java.math.BigInteger;
import java.util.List;

@Mapper
public interface PictureMapper {

    @Insert("insert into picture (url) values(#{url})")
    @Options(useGeneratedKeys = true,keyProperty = "picture_id",keyColumn = "picture_id")
    public int addPicture(Picture picture);

    @Delete("delete from picture where picture_id=#{picture_id}")
    public int deletePictureById(BigInteger id);

    @Select("select * from picture where picture_id = #{id}")
    public Picture getPictureById(BigInteger id);

    @Select("select * from picture")
    public List<Picture> getAllPicture();

    @Update("update picture set url=#{url} where picture_id=#{picture_id}")
    public int updatePicture(Picture picture);

}
