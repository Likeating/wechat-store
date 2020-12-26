package com.fortwelve.wechatstore.dao;


import com.fortwelve.wechatstore.pojo.Customer;
import com.fortwelve.wechatstore.pojo.Turnover;
import org.apache.ibatis.annotations.*;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;

@Mapper
public interface StatisticMapper {
//    SELECT sum(sku_price*num) as 'turnover' ,sum(num) as 'product_num', count(distinct(order_info.order_id)) as 'order_num'
//    FROM order_info,order_detail
//    where
//    order_info.order_id = order_detail.order_id and order_detail.product_id = 2
    @Select("<script>" +
            "SELECT sum(sku_price*num) as 'turnover' ,sum(num) as 'product_num', count(distinct(order_info.order_id)) as 'order_num' " +
                "FROM order_info,order_detail " +
            "where " +
                "order_info.order_id = order_detail.order_id and order_info.order_status != 4 and order_info.order_status != 5" +
                "<if test='product_id != null'> and order_detail.product_id = #{product_id} </if>" +
                "<if test='start_time != null'> and <![CDATA[ order_info.create_time >= #{start_time} ]]> </if>" +
                "<if test='end_time != null'> and <![CDATA[ order_info.create_time <= #{end_time} ]]> </if>" +
            "</script>"
    )
    public Turnover getTurnover(BigInteger product_id, Timestamp start_time,Timestamp end_time);


}
