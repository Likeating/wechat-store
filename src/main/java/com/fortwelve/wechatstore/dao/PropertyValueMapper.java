package com.fortwelve.wechatstore.dao;

import com.fortwelve.wechatstore.pojo.PropertyValue;
import org.apache.ibatis.annotations.*;

import java.math.BigInteger;
import java.util.List;

@Mapper
public interface PropertyValueMapper {

    @Insert("insert into property_value (product_id,value_name,picture_id) values (#{product_id},#{value_name},#{picture_id})")
    @Options(useGeneratedKeys = true,keyProperty = "value_id",keyColumn = "value_id")
    public int addPropertyValue(PropertyValue propertyValue);

    @Update("update property_value set product_id=#{product_id},value_name=#{value_name},picture_id=#{picture_id} where value_id=#{value_id}")
    public int updatePropertyValue(PropertyValue propertyValue);

    @Delete("delete from property_value where value_id=#{id}")
    public int deletePropertyValueById(BigInteger id);

    @Select("select * from property_value where value_id = #{id}")
    public PropertyValue getPropertyValueById(BigInteger id);

    @Select("select * from property_value")
    public List<PropertyValue> getAllPropertyValue();

    @Select("select * from property_value where product_id=#{product_id}")
    public List<PropertyValue> getAllPropertyValueByProduct_id(BigInteger product_id);
}
