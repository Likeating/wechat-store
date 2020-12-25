package com.fortwelve.wechatstore.dao;

import com.fortwelve.wechatstore.pojo.OrderInfo;
import org.apache.ibatis.annotations.*;

import java.math.BigInteger;
import java.util.List;

@Mapper
public interface OrderInfoMapper {

    @Insert("insert into order_info (customer_id,total_price,freight_price,pay_price,address,order_status,create_time,note) values (#{customer_id},#{total_price},#{freight_price},#{pay_price},#{address},#{order_status},#{create_time},#{note})")
    @Options(useGeneratedKeys = true,keyProperty = "order_id",keyColumn = "order_id")
    public int addOrderInfo(OrderInfo orderInfo);

    @Update("update order_info set customer_id=#{customer_id},total_price=#{total_price},freight_price=#{freight_price},pay_price=#{pay_price},address=#{address},order_status=#{order_status},create_time=#{create_time},note=#{note} where order_id=#{order_id}")
    public int updateOrderInfo(OrderInfo orderInfo);

    @Delete("delete from order_info where order_id=#{id}")
    public int deleteOrderInfoById(BigInteger id);

    @Select("select * from order_info where order_id = #{id}")
    public OrderInfo getOrderInfoById(BigInteger id);

//    @Select("select * from order_info")
//    public List<OrderInfo> getAllOrderInfo();

    //大于小于号要用特殊字符 &gt; &lt; 代替
    //或者是放在 <![CDATA[  表达式  ]]>中
    @Select("<script> select * from order_info "+
            "<where>"+
            "<if test='customer_id!=null'> customer_id=#{customer_id}</if>"+
                "<if test='order_status!=null'>"+
                    "<choose>"+
                        "<when test='order_status == -1'> and <![CDATA[order_status >= 0 and order_status<=4 ]]></when>"+
                        "<when test='order_status == -2'> and <![CDATA[order_status >= 0 and order_status<=5 ]]></when>"+
                        "<otherwise> and order_status=#{order_status} </otherwise>"+
                    "</choose>"+
                "</if>"+
            "</where>"+
            "<if test='sort != null'>"+
                "<if test='sort == 1'> order by create_time desc</if>"+
                "<if test='sort == 2'> order by create_time asc</if>"+
            "</if>"+
            "<if test='rows!=null'> limit <if test='offset!=null'>#{offset},</if>#{rows}</if>"+
            "</script>")
    public List<OrderInfo> getAllOrderInfo(BigInteger customer_id,Integer order_status,Integer sort,Integer offset, Integer rows);

}
