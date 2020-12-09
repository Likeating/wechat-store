package com.fortwelve.wechatstore.service;

import com.fortwelve.wechatstore.dao.CustomerMapper;
import com.fortwelve.wechatstore.dto.UserInfo;
import com.fortwelve.wechatstore.pojo.Customer;

import java.math.BigInteger;
import java.util.List;

public interface CustomerService {

    int addCustomer(Customer customer);

    int deleteCustomerById(BigInteger id);

    Customer getCustomerById(BigInteger id);

    List<Customer> getAllCustomer();

    int updateCustomer(Customer customer);

    Customer getCustomerByOpenId(String openid);

    UserInfo getUserInfo(Customer customer);

//    UserInfo getUserInfo(Customer customer);
}
