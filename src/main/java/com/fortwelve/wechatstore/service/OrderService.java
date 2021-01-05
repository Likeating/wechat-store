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

    /**
     * 原则上，订单不允许删除，只允许改变状态，5.（无效订单） 等同于 删除
     * @param id
     * @return
     */
    int deleteOrderInfoById(BigInteger id);
    OrderInfo getOrderInfoById(BigInteger id);
//    List<OrderInfo> getAllOrderInfo();


    /**
     * 创建订单，并扣除相关商品库存
     * @param orderInfo 订单主要信息
     * @param orderDetails 订单商品详细信息，可以包括多个商品
     * @throws OrderException
     * @throws JsonProcessingException
     */
    void createOrder(OrderInfo orderInfo,List<OrderDetail> orderDetails) throws OrderException, JsonProcessingException;


    /**
     * 订单支付
     * @param id 订单id
     * @param customer_id 客户id
     * @throws OrderException
     */
    void payOrderById(BigInteger id,BigInteger customer_id)throws OrderException;

    /**
     * 查询该客户所有订单
     * @param customer_id 客户id  null则表示全部客户
     * @param order_status 订单状态 null则表示所有状态
     *                     -1表示有效订单(0-4), -2表示全部(0-5)
     *                     0.未支付；1.待发货；2.已发货；3.已完成；4.已关闭；5.无效订单
     * @param sort 排序方式 null或0表示不排序
     *             1.desc 降序 2.asc升序
     * @return 所有订单主信息
     */
    List<OrderInfo> getAllOrderInfo(BigInteger customer_id,Integer order_status,Integer sort,Integer offset, Integer rows);

    /**
     * 查询订单详细信息
     * @param order_id
     * @return 订单详细信息
     */
    List<OrderDetail> getAllOrderDetailByOrder_id(BigInteger order_id);

    /**
     * 关闭订单，并恢复相关商品库存
     * @param order_id
     * @return
     */
    void closeOrderById(BigInteger order_id) throws OrderException;

    void updateOrderStatus(BigInteger order_id,int status) throws OrderException;
}
