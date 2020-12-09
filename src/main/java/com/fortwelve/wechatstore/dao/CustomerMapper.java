package com.fortwelve.wechatstore.dao;

import com.fortwelve.wechatstore.pojo.Customer;
import org.apache.ibatis.annotations.*;

import java.math.BigInteger;
import java.util.List;

@Mapper
public interface CustomerMapper {

    @Insert("insert into customer (openid,nickName,gender,wxlanguage,city,province,country,avatarUrl,create_time) values (#{openid},#{nickName},#{gender},#{language},#{city},#{province},#{country},#{avatarUrl},#{create_time})")
    @Options(useGeneratedKeys = true,keyProperty = "customer_id",keyColumn = "customer_id")
    public int addCustomer(Customer customer);

    @Delete("delete from customer where customer_id=#{id}")
    public int deleteCustomerById(BigInteger id);

    @Select("select * from customer where customer_id = #{id}")
    public Customer getCustomerById(BigInteger id);

    @Select("select * from customer where openid = #{openid}")
    public Customer getCustomerByOpenId(String openid);

    @Select("select * from customer")
    public List<Customer> getAllCustomer();

    @Update("update customer set nickName=#{nickName},gender=#{gender},wxlanguage=#{language},city=#{city},province=#{province},country=#{country},avatarUrl=#{avatarUrl} where customer_id=#{customer_id}")
    public int updateCustomer(Customer customer);


}
