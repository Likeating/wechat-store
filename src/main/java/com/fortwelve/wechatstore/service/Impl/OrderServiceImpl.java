package com.fortwelve.wechatstore.service.Impl;

import com.fortwelve.wechatstore.dao.CategoryMapper;
import com.fortwelve.wechatstore.dao.OrderDetailMapper;
import com.fortwelve.wechatstore.dao.OrderInfoMapper;
import com.fortwelve.wechatstore.pojo.Category;
import com.fortwelve.wechatstore.pojo.OrderDetail;
import com.fortwelve.wechatstore.pojo.OrderInfo;
import com.fortwelve.wechatstore.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderInfoMapper orderInfoMapper;
    @Autowired
    OrderDetailMapper orderDetailMapper;


    Logger logger = LoggerFactory.getLogger(this.getClass());
//    {
//        logger = LoggerFactory.getLogger(this.getClass());
//    }

    @Override
    public int addOrderInfo(OrderInfo orderInfo) {
        return orderInfoMapper.addOrderInfo(orderInfo);
    }

    @Override
    public int updateOrderInfo(OrderInfo orderInfo) {
        return orderInfoMapper.updateOrderInfo(orderInfo);
    }

    @Override
    public int deleteOrderInfoById(BigInteger id) {
        return orderInfoMapper.deleteOrderInfoById(id);
    }

    @Override
    public OrderInfo getOrderInfoById(BigInteger id) {
        return orderInfoMapper.getOrderInfoById(id);
    }

    @Override
    public List<OrderInfo> getAllOrderInfo() {
        return orderInfoMapper.getAllOrderInfo();
    }

    @Transactional
    @Override
    public boolean createOrder(OrderInfo orderInfo,List<OrderDetail> orderDetails) {

        orderInfoMapper.addOrderInfo(orderInfo);
        BigInteger order_id = orderInfo.getOrder_id();

        for(OrderDetail orderDetail : orderDetails){
            orderDetail.setOrder_id(order_id);
            orderDetailMapper.addOrderDetail(orderDetail);
        }
        return true;
    }


}
