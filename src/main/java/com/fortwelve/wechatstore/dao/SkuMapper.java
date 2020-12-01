package com.fortwelve.wechatstore.dao;

import com.fortwelve.wechatstore.pojo.Sku;
import org.apache.ibatis.annotations.*;

import java.math.BigInteger;
import java.util.List;

@Mapper
public interface SkuMapper {

    @Insert("insert into sku (product_id,properties,sku_price,picture_id) values (#{product_id},#{properties},#{sku_price},#{picture_id})")
    @Options(useGeneratedKeys = true,keyProperty = "sku_id",keyColumn = "sku_id")
    public int addSku(Sku sku);

    @Update("update sku set product_id=#{product_id},properties=#{properties},sku_price=#{sku_price},picture_id=#{picture_id} where sku_id=#{sku_id}")
    public int updateSku(Sku sku);

    @Delete("delete from sku where sku_id=#{id}")
    public int deleteSkuById(BigInteger id);

    @Select("select * from sku where sku_id = #{id}")
    public Sku getSkuById(BigInteger id);

    @Select("select * from sku")
    public List<Sku> getAllSku();
}
