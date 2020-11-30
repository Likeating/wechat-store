package com.fortwelve.wechatstore.mapper;

import com.fortwelve.wechatstore.pojo.PropertyKey;
import org.apache.ibatis.annotations.*;

import java.math.BigInteger;
import java.util.List;

@Mapper
public interface PropertyKeyMapper {

    @Insert("insert into property_key (key_name,isPicture) values (#{key_name},#{isPicture})")
    @Options(useGeneratedKeys = true,keyProperty = "key_id",keyColumn = "key_id")
    public int addPropertyKey(PropertyKey propertyKey);

    @Update("update property_key set key_name=#{key_name},isPicture=#{isPicture} where key_id=#{key_id}")
    public int updatePropertyKey(PropertyKey propertyKey);

    @Delete("delete from property_key where key_id=#{id}")
    public int deletePropertyKeyById(BigInteger id);

    @Select("select * from property_key where key_id = #{id}")
    public PropertyKey getPropertyKeyById(BigInteger id);

    @Select("select * from property_key")
    public List<PropertyKey> getAllPropertyKey();
}
