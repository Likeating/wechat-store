package com.fortwelve.wechatstore.service;

import com.fortwelve.wechatstore.pojo.OrderDetail;
import com.fortwelve.wechatstore.pojo.OrderInfo;

import java.math.BigInteger;
import java.util.List;

public interface OrderService {

    /**
     * 别用这个
     * @param orderInfo
     * @return
     */
    int addOrderInfo(OrderInfo orderInfo);
    int updateOrderInfo(OrderInfo orderInfo);
    int deleteOrderInfoById(BigInteger id);
    OrderInfo getOrderInfoById(BigInteger id);
    List<OrderInfo> getAllOrderInfo();

    /**
     * 创建订单
     * @param orderInfo 订单主要信息
     * @param orderDetails 订单商品详细信息，可以包括多个商品
     * @return 成功返回true
     */
    boolean createOrder(OrderInfo orderInfo,List<OrderDetail> orderDetails);

}
