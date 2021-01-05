package com.fortwelve.wechatstore.dao;

import com.fortwelve.wechatstore.pojo.PropertyKey;
import org.apache.ibatis.annotations.*;

import java.math.BigInteger;
import java.util.List;

@Mapper
public interface PropertyKeyMapper {

    @Insert("insert into property_key (product_id,key_name,isPicture) values (#{product_id},#{key_name},#{isPicture})")
    @Options(useGeneratedKeys = true,keyProperty = "key_id",keyColumn = "key_id")
    public int addPropertyKey(PropertyKey propertyKey);

    @Update("update property_key set product_id=#{product_id},key_name=#{key_name},isPicture=#{isPicture} where key_id=#{key_id}")
    public int updatePropertyKey(PropertyKey propertyKey);

    @Delete("delete from property_key where key_id=#{id}")
    public int deletePropertyKeyById(BigInteger id);

    @Select("select * from property_key where key_id = #{id}")
    public PropertyKey getPropertyKeyById(BigInteger id);

    @Select("select * from property_key")
    public List<PropertyKey> getAllPropertyKey();

    @Select("select * from property_key where product_id=#{product_id}")
    public List<PropertyKey> getAllPropertyKeyByProduct_id(BigInteger product_id);
}
