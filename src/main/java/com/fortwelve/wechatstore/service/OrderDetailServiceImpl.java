package com.fortwelve.wechatstore.service;

import com.fortwelve.wechatstore.dao.OrderDetailMapper;
import com.fortwelve.wechatstore.pojo.OrderDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
public class OrderDetailServiceImpl implements OrderDetailService{
    @Autowired
    OrderDetailMapper orderDetailMapper;
    public int addOrderDetail(OrderDetail orderDetail){
        return orderDetailMapper.addOrderDetail(orderDetail);
    }
    public int updateOrderDetail(OrderDetail orderDetail){
        return orderDetailMapper.updateOrderDetail(orderDetail);
    }
    public int deleteOrderDetailById(BigInteger id){
        return orderDetailMapper.deleteOrderDetailById(id);
    }
    public OrderDetail getOrderDetailById(BigInteger id){
        return orderDetailMapper.getOrderDetailById(id);
    }
    public List<OrderDetail> getAllOrderDetail(){
        return orderDetailMapper.getAllOrderDetail();
    }
}
