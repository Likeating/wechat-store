package com.fortwelve.wechatstore.service;
import com.fortwelve.wechatstore.dao.OrderInfoMapper;
import com.fortwelve.wechatstore.pojo.OrderInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
public class OrderInfoServiceImpl implements OrderInfoService {
    @Autowired
    OrderInfoMapper orderInfoMapper;
    public int addOrderInfo(OrderInfo orderInfo){
        return orderInfoMapper.addOrderInfo(orderInfo);
    }
    public int updateOrderInfo(OrderInfo orderInfo){
        return orderInfoMapper.updateOrderInfo(orderInfo);
    }
    public int deleteOrderInfoById(BigInteger id){
        return orderInfoMapper.deleteOrderInfoById(id);
    }
    public OrderInfo getOrderInfoById(BigInteger id) {
        return orderInfoMapper.getOrderInfoById(id);

    }
    public List<OrderInfo> getAllOrderInfo(){
        return orderInfoMapper.getAllOrderInfo();
    }
}
