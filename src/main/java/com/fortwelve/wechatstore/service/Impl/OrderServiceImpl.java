package com.fortwelve.wechatstore.service.Impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fortwelve.wechatstore.dao.*;
import com.fortwelve.wechatstore.pojo.*;
import com.fortwelve.wechatstore.service.OrderService;
import com.fortwelve.wechatstore.util.OrderException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.*;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderInfoMapper orderInfoMapper;
    @Autowired
    OrderDetailMapper orderDetailMapper;
    @Autowired
    SkuMapper skuMapper;
    @Autowired
    PropertyKeyMapper keyMapper;
    @Autowired
    PropertyValueMapper valueMapper;
    @Autowired
    ObjectMapper objectMapper;

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
    public boolean createOrder(OrderInfo orderInfo,List<OrderDetail> orderDetails) throws OrderException, JsonProcessingException {
        //价格计算并检查库存
        Sku sku;
        BigDecimal totalPrice=new BigDecimal("0");
        BigDecimal payPrice=new BigDecimal("0");
        List<Map<String,String>> listattr = new LinkedList<>();
        Map<String,String> attr=null;

        for(OrderDetail orderDetail : orderDetails){
            sku = skuMapper.getSkuById(orderDetail.getSku_id());
            //设置详细订单的商品属性
            String properties = sku.getProperties();
            String arr1 [] = properties.split("_");

            for(String tmp : arr1){
                PropertyKey key=keyMapper.getPropertyKeyById(new BigInteger(tmp.split(":")[0]));
                PropertyValue value=valueMapper.getPropertyValueById(new BigInteger(tmp.split(":")[1]));
                attr = new HashMap<>();
                attr.put("key",key.getKey_name());
                attr.put("value",value.getValue_name());
                listattr.add(attr);
            }

            orderDetail.setSku_attr(objectMapper.writeValueAsString(listattr));
            orderDetail.setSku_price(sku.getSku_price());
            if(sku.getStock()<orderDetail.getNum()){
                throw new OrderException("库存不足。",606);
            }
            totalPrice=totalPrice.add(
                    sku.getSku_price().multiply(BigDecimal.valueOf(orderDetail.getNum()))
            );
        }
        orderInfo.setTotal_price(totalPrice);
        //实际支付价格=总价格+运费
        orderInfo.setPay_price(totalPrice.add(orderInfo.getFreight_price()));

        Calendar calendar = Calendar.getInstance();
        //订单创建时间
        orderInfo.setCreate_time(new Timestamp(calendar.getTimeInMillis()));
        //未支付
        orderInfo.setOrder_status(0);
        //创建订单主信息
        orderInfoMapper.addOrderInfo(orderInfo);

        BigInteger order_id = orderInfo.getOrder_id();

        for(OrderDetail orderDetail : orderDetails){
            orderDetail.setOrder_id(order_id);
            orderDetailMapper.addOrderDetail(orderDetail);
        }
        return true;
    }


}
