package com.fortwelve.wechatstore.mapper;

import com.fortwelve.wechatstore.pojo.Customer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CustomerMapper {
    @Select("select * from customer where customer_id = #{id}")
    public Customer getCustomerById(Long id);
}
