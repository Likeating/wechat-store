package com.fortwelve.wechatstore.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fortwelve.wechatstore.pojo.OrderDetail;
import com.fortwelve.wechatstore.pojo.OrderInfo;
import com.fortwelve.wechatstore.util.OrderException;

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
    boolean createOrder(OrderInfo orderInfo,List<OrderDetail> orderDetails) throws OrderException, JsonProcessingException;

    /**
     * 订单支付
     * @param id 订单id
     * @param customer_id 客户id
     * @return 支付后的订单主信息
     * @throws OrderException
     */
    OrderInfo payOrderById(BigInteger id,BigInteger customer_id)throws OrderException;

    /**
     * 查询该客户所有订单
     * @param customer_id
     * @return 所有订单主信息
     */
    List<OrderInfo> getAllOrderInfoByCustomer_id(BigInteger customer_id);

    /**
     * 查询订单详细信息
     * @param order_id
     * @return 订单详细信息
     */
    List<OrderDetail> getAllOrderDetailByOrder_id(BigInteger order_id,BigInteger customer_id) throws OrderException;

}
