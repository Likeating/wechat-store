package com.fortwelve.wechatstore.service;

import com.fortwelve.wechatstore.pojo.OrderInfo;

import java.math.BigInteger;
import java.util.List;

public interface OrderInfoService {
    int addOrderInfo(OrderInfo orderInfo);
    int updateOrderInfo(OrderInfo orderInfo);
    int deleteOrderInfoById(BigInteger id);
    OrderInfo getOrderInfoById(BigInteger id);
    List<OrderInfo> getAllOrderInfo();
}
