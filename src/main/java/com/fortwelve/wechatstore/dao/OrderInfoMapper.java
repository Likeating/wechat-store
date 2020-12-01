package com.fortwelve.wechatstore.dao;

import com.fortwelve.wechatstore.pojo.OrderInfo;
import org.apache.ibatis.annotations.*;

import java.math.BigInteger;
import java.util.List;

@Mapper
public interface OrderInfoMapper {

    @Insert("insert into order_info (customer_id,total_price,freight_price,pay_price,address,order_status,create_time,note) values (#{customer_if},#{total_price},#{freight_price},#{pay_price},#{address},#{order_status},#{create_time},#{note})")
    @Options(useGeneratedKeys = true,keyProperty = "order_id",keyColumn = "order_id")
    public int addOrderInfo(OrderInfo orderInfo);

    @Update("update order_info set customer_id=#{customer_if},total_price=#{total_price},freight_price=#{freight_price},pay_price=#{pay_price},address=#{address},order_status=#{order_status},create_time=#{create_time},note=#{note} where order_id=#{order_id}")
    public int updateOrderInfo(OrderInfo orderInfo);

    @Delete("delete from order_info where order_id=#{id}")
    public int deleteOrderInfoById(BigInteger id);

    @Select("select * from order_info where order_id = #{id}")
    public OrderInfo getOrderInfoById(BigInteger id);

    @Select("select * from order_info")
    public List<OrderInfo> getAllOrderInfo();
}
