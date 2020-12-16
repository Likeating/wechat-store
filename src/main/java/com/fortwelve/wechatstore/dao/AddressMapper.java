package com.fortwelve.wechatstore.dao;

import com.fortwelve.wechatstore.pojo.Address;
import com.fortwelve.wechatstore.pojo.Category;
import org.apache.ibatis.annotations.*;

import java.math.BigInteger;
import java.util.List;

@Mapper
public interface AddressMapper {

    @Insert("insert into address (customer_id,recipient,telnumber,address_detail) values (#{customer_id},#{recipient},#{telnumber},#{address_detail})")
    @Options(useGeneratedKeys = true,keyProperty = "address_id",keyColumn = "address_id")
    public int addAddress(Address address);

    @Delete("delete from address where address_id=#{address_id}")
    public int deleteAddressById(BigInteger address_id);

    @Update("update address set customer_id=#{customer_id},recipient=#{recipient},telnumber=#{telnumber},address_detail=#{address_detail} where address_id=#{address_id}")
    public int updateAddress(Address address);

    @Select("select * from address where address_id=#{address_id}")
    public Address getAddressById(BigInteger address_id);

    @Select("select * from address")
    public List<Address> getAddressByCustomerId(BigInteger customer_id);

    @Select("select * from address")
    public List<Address> getAllAddress();

}
