package com.fortwelve.wechatstore.mapper;

import com.fortwelve.wechatstore.pojo.OrderDetail;
import org.apache.ibatis.annotations.*;

import java.math.BigInteger;
import java.util.List;

@Mapper
public interface OrderDetailMapper {
    @Insert("insert into order_detail (order_id,sku_id,sku_attr,sku_price,num) values (#{order_id},#{sku_id},#{sku_attr},#{sku_price},#{num})")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    public int addOrderDetail(OrderDetail orderDetail);

    @Update("update order_detail set order_id=#{order_id},sku_id=#{sku_id},sku_attr=#{sku_attr},sku_price=#{sku_price},num=#{num} where id=#{id}")
    public int updateOrderDetail(OrderDetail orderDetail);

    @Delete("delete from order_detail where id=#{id}")
    public int deleteOrderDetailById(BigInteger id);

    @Select("select * from order_detail where id = #{id}")
    public OrderDetail getOrderDetailById(BigInteger id);

    @Select("select * from order_detail")
    public List<OrderDetail> getAllOrderDetail();
}
