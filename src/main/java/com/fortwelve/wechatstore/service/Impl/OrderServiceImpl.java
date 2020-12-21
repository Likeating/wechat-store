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
    ProductMapper productMapper;
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

    @Transactional
    @Override
    public int updateOrderInfo(OrderInfo orderInfo) {
        return orderInfoMapper.updateOrderInfo(orderInfo);
    }

    @Transactional
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

        Map<String,String> attr=null;

        for(OrderDetail orderDetail : orderDetails){
            sku = skuMapper.getSkuById(orderDetail.getSku_id());
            Product product = productMapper.getProductById(sku.getProduct_id());

            if(null == sku || null == product){
                throw new OrderException("商品不存在。",608);
            }

            if(null != product.getDelete_time()){
                throw new OrderException("商品已下架。",607);
            }
            //设置详细订单的商品属性
            String properties = sku.getProperties();
            String arr1 [] = properties.split("_");
            List<Map<String,String>> listattr = new LinkedList<>();
            for(String tmp : arr1){
                PropertyKey key=keyMapper.getPropertyKeyById(new BigInteger(tmp.split(":")[0]));
                PropertyValue value=valueMapper.getPropertyValueById(new BigInteger(tmp.split(":")[1]));
                attr = new HashMap<>();
                attr.put("key",key.getKey_name());
                attr.put("value",value.getValue_name());
                listattr.add(attr);
            }
            orderDetail.setProduct_name(product.getProduct_name());
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

        if(0 == orderInfoMapper.addOrderInfo(orderInfo)){
            return false;
        }
        BigInteger order_id = orderInfo.getOrder_id();

        for(OrderDetail orderDetail : orderDetails){
            orderDetail.setOrder_id(order_id);
            if(0 == orderDetailMapper.addOrderDetail(orderDetail)){
                return false;
            }
        }
        return true;
    }


    @Transactional
    @Override
    public OrderInfo payOrderById(BigInteger id,BigInteger customer_id) throws OrderException {
        OrderInfo orderInfo = orderInfoMapper.getOrderInfoById(id);
        if (null == orderInfo){
            throw new OrderException("编号为："+id+"的订单不存在。",609);
        }
        if (!orderInfo.getCustomer_id().equals(customer_id)){
            throw new OrderException("无权操作订单。",611);
        }
        if(0 != orderInfo.getOrder_status()){
            throw new OrderException("订单状态出错。",610);
        }
        orderInfo.setOrder_status(1);
        if(0 == orderInfoMapper.updateOrderInfo(orderInfo)){
            return null;
        }
        return orderInfo;
    }

    @Override
    public List<OrderInfo> getAllOrderInfoByCustomer_idAndOrder_status(BigInteger customer_id,int order_status,int sort) {
        return orderInfoMapper.getAllOrderInfoByCustomer_idAndOrder_status(customer_id,order_status,sort);
    }

    @Override
    public List<OrderDetail> getAllOrderDetailByOrder_id(BigInteger order_id,BigInteger customer_id) throws OrderException{

//        OrderInfo orderInfo = orderInfoMapper.getOrderInfoById(order_id);
//
//        if(!orderInfo.getCustomer_id().equals(customer_id)){
//            throw new OrderException("无权操作订单",611);
//        }
        return orderDetailMapper.getAllOrderDetailByOrder_id(order_id);
    }
}
