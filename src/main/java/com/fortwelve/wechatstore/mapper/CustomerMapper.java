package com.fortwelve.wechatstore.mapper;

import com.fortwelve.wechatstore.pojo.Customer;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CustomerMapper {

    @Insert("insert into customer (customer_name) values (#{customer_name})")
    @Options(useGeneratedKeys = true,keyProperty = "customer_id",keyColumn = "customer_id")
    public int addCustomer(Customer customer);

    @Delete("delete from customer where customer_id=#{id}")
    public int deleteCustomerById(java.math.BigInteger id);

    @Select("select * from customer where customer_id = #{id}")
    public Customer getCustomerById(java.math.BigInteger id);

    @Select("select * from customer")
    public List<Customer> getAllCustomer();

    @Update("update customer set customer_name=#{customer_name} where customer_id=#{customer_id}")
    public int updateCustomer(Customer customer);


}
