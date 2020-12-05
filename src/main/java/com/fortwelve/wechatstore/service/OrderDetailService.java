package com.fortwelve.wechatstore.service;

import com.fortwelve.wechatstore.pojo.OrderDetail;

import java.math.BigInteger;
import java.util.List;

public interface OrderDetailService {
    int addOrderDetail(OrderDetail orderDetail);
    int updateOrderDetail(OrderDetail orderDetail);
    int deleteOrderDetailById(BigInteger id);
    OrderDetail getOrderDetailById(BigInteger id);
    List<OrderDetail> getAllOrderDetail();
}
