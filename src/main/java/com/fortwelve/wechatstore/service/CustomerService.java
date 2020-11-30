package com.fortwelve.wechatstore.service;

import com.fortwelve.wechatstore.mapper.CustomerMapper;
import com.fortwelve.wechatstore.pojo.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
public class CustomerService {
    @Autowired
    CustomerMapper customerMapper;

    public int addCustomer(Customer customer){
        return customerMapper.addCustomer(customer);
    }
    public int deleteCustomerById(BigInteger id){
        return customerMapper.deleteCustomerById(id);
    }
    public Customer getCustomerById(BigInteger id){
        return customerMapper.getCustomerById(id);
    }
    public List<Customer> getAllCustomer(){
        return customerMapper.getAllCustomer();
    }
    public int updateCustomer(Customer customer){
        return customerMapper.updateCustomer(customer);
    }
}
