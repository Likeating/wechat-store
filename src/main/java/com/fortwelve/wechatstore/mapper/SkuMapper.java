package com.fortwelve.wechatstore.mapper;

import com.fortwelve.wechatstore.pojo.Sku;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SkuMapper {

    @Insert("insert into sku (product_id,entries,sku_price,picture_id) values (#{product_id},#{entries},#{sku_price},#{price_id})")
    @Options(useGeneratedKeys = true,keyProperty = "sku_id",keyColumn = "sku_id")
    public int addSku(Sku sku);

    @Update("update sku set product_id=#{product_id},entries=#{entries},sku_price=#{sku_price},picture_id=#{price_id} where sku_id=#{sku_id}")
    public int updateSku(Sku sku);

    @Delete("delete from sku where sku_id=#{id}")
    public int deleteSkuById(java.math.BigInteger id);

    @Select("select * from sku where sku_id = #{id}")
    public Sku getSkuById(java.math.BigInteger id);

    @Select("select * from sku")
    public List<Sku> getAllSku();
}
