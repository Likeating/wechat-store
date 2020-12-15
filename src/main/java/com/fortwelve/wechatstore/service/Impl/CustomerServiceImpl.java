package com.fortwelve.wechatstore.service.Impl;

import com.fortwelve.wechatstore.dao.CustomerMapper;
import com.fortwelve.wechatstore.dto.UserInfo;
import com.fortwelve.wechatstore.pojo.Customer;
import com.fortwelve.wechatstore.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerMapper customerMapper;

    @Override
    public int addCustomer(Customer customer) {
        Calendar calendar = Calendar.getInstance();
        customer.setCreate_time(new Timestamp(calendar.getTimeInMillis()));
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
    public UserInfo CustomerToUserInfo(Customer customer) {
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

    @Override
    public Customer UserInfoToCustomer(UserInfo userInfo,String openid) {

        return new Customer(
                userInfo.getUserId(),
                openid,
                userInfo.getNickName(),
                userInfo.getGender(),
                userInfo.getLanguage(),
                userInfo.getCity(),
                userInfo.getProvince(),
                userInfo.getCountry(),
                userInfo.getAvatarUrl(),
                userInfo.getCreate_time()
        );
    }
}
