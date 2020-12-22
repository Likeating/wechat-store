package com.fortwelve.wechatstore.dao;

import com.fortwelve.wechatstore.pojo.OrderDetail;
import org.apache.ibatis.annotations.*;

import java.math.BigInteger;
import java.util.List;

@Mapper
public interface OrderDetailMapper {
    @Insert("insert into order_detail (order_id,product_id,sku_id,product_name,sku_attr,sku_price,num,picture) values (#{order_id},#{product_id},#{sku_id},#{product_name},#{sku_attr},#{sku_price},#{num},#{picture})")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    public int addOrderDetail(OrderDetail orderDetail);

    @Update("update order_detail set order_id=#{order_id},product_id=#{product_id},sku_id=#{sku_id},product_name=#{product_name},sku_attr=#{sku_attr},sku_price=#{sku_price},num=#{num},picture=#{picture} where id=#{id}")
    public int updateOrderDetail(OrderDetail orderDetail);

    @Delete("delete from order_detail where id=#{id}")
    public int deleteOrderDetailById(BigInteger id);

    @Select("select * from order_detail where id = #{id}")
    public OrderDetail getOrderDetailById(BigInteger id);

    @Select("select * from order_detail")
    public List<OrderDetail> getAllOrderDetail();

    @Select("select * from order_detail where order_id = #{order_id}")
    public List<OrderDetail> getAllOrderDetailByOrder_id(BigInteger order_id);
}
