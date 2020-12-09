package com.fortwelve.wechatstore.service.Impl;

import com.fortwelve.wechatstore.dao.CustomerMapper;
import com.fortwelve.wechatstore.dto.UserInfo;
import com.fortwelve.wechatstore.pojo.Customer;
import com.fortwelve.wechatstore.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerMapper customerMapper;

    @Override
    public int addCustomer(Customer customer) {
        return customerMapper.addCustomer(customer);
    }

    @Override
    public int deleteCustomerById(BigInteger id) {
        return customerMapper.deleteCustomerById(id);
    }

    @Override
    public Customer getCustomerById(BigInteger id) {
        return customerMapper.getCustomerById(id);
    }

    @Override
    public List<Customer> getAllCustomer() {
        return customerMapper.getAllCustomer();
    }

    @Override
    public int updateCustomer(Customer customer) {
        return customerMapper.updateCustomer(customer);
    }

    @Override
    public Customer getCustomerByOpenId(String openid) {
        return customerMapper.getCustomerByOpenId(openid);
    }

    @Override
    public UserInfo getUserInfo(Customer customer) {
        UserInfo userInfo = new UserInfo(
                customer.getCustomer_id(),
                customer.getNickName(),
                customer.getGender(),
                customer.getLanguage(),
                customer.getCity(),
                customer.getProvince(),
                customer.getCountry(),
                customer.getAvatarUrl(),
                customer.getCreate_time()
        );
        return userInfo;
    }
}
