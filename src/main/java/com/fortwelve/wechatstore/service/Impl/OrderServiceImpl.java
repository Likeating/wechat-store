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
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.*;

@Service("orderService")
@Transactional(isolation = Isolation.REPEATABLE_READ,rollbackFor = Exception.class)
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
    @Autowired
    PictureMapper pictureMapper;

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

//    @Override
//    public List<OrderInfo> getAllOrderInfo() {
//        return orderInfoMapper.getAllOrderInfo();
//    }

    @Override
    public void createOrder(OrderInfo orderInfo,List<OrderDetail> orderDetails) throws OrderException, JsonProcessingException {
        //价格计算并检查库存
        Sku sku;
        BigDecimal totalPrice=new BigDecimal("0");
        BigDecimal payPrice=new BigDecimal("0");

        Map<String,String> attr=null;
        for(OrderDetail orderDetail : orderDetails){

            if(null == orderDetail.getSku_id() || orderDetail.getNum()<=0){
                throw new OrderException("参数不正确。",701);
            }
            sku = skuMapper.getSkuById(orderDetail.getSku_id());
            Product product = productMapper.getProductById(sku.getProduct_id());

            if(null == sku || null == product){
                throw new OrderException("商品不存在。",608);
            }

            if(null != product.getDelete_time() && System.currentTimeMillis()>product.getDelete_time().getTime()){
                throw new OrderException("商品已下架。",607);
            }
            //填写详细订单的商品属性 start
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
            orderDetail.setSku_attr(objectMapper.writeValueAsString(listattr));
            //填写详细订单的商品属性 end
            orderDetail.setProduct_id(product.getProduct_id());
            orderDetail.setProduct_name(product.getProduct_name());
            orderDetail.setSku_price(sku.getSku_price());
            orderDetail.setPicture(pictureMapper.getPictureById(sku.getPicture_id()).getUrl());

            if(sku.getStock()<orderDetail.getNum()){
                throw new OrderException("库存不足。",606);
            }
            //减少库存
            sku.setStock(sku.getStock()-orderDetail.getNum());
            //增加销量
            product.setSale(product.getSale()+orderDetail.getNum());
            totalPrice=totalPrice.add(
                    sku.getSku_price().multiply(BigDecimal.valueOf(orderDetail.getNum()))
            );
            skuMapper.updateSku(sku);
            productMapper.updateProduct(product);
        }
        orderInfo.setTotal_price(totalPrice);
        //实际支付价格=总价格+运费
        orderInfo.setPay_price(totalPrice.add(orderInfo.getFreight_price()));

        //订单创建时间
        orderInfo.setCreate_time(new Timestamp(System.currentTimeMillis()));
        //未支付
        orderInfo.setOrder_status(0);
        //创建订单主信息

        if(0 == orderInfoMapper.addOrderInfo(orderInfo)){
            throw new OrderException("订单操作失败。",612);
        }
        BigInteger order_id = orderInfo.getOrder_id();

        for(OrderDetail orderDetail : orderDetails){
            orderDetail.setOrder_id(order_id);
            if(0 == orderDetailMapper.addOrderDetail(orderDetail)){
                throw new OrderException("订单操作失败。",612);
            }
        }

    }


    @Override
    public void payOrderById(BigInteger id,BigInteger customer_id) throws OrderException {
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
            throw new OrderException("订单操作失败。",612);
        }
    }

    @Override
    public List<OrderInfo> getAllOrderInfo(BigInteger customer_id,Integer order_status,Integer sort,Integer offset, Integer rows) {
        return orderInfoMapper.getAllOrderInfo(customer_id,order_status,sort,offset,rows);
    }

    @Override
    public List<OrderDetail> getAllOrderDetailByOrder_id(BigInteger order_id){
        return orderDetailMapper.getAllOrderDetailByOrder_id(order_id);
    }

    @Override
    public void closeOrderById(BigInteger order_id) throws OrderException{
        OrderInfo orderInfo = orderInfoMapper.getOrderInfoById(order_id);

        if(null == orderInfo){
            throw new OrderException("订单不存在。",609);
        }
        if(orderInfo.getOrder_status()!=0){
            throw new OrderException("订单状态出错。",610);
        }
        orderInfo.setOrder_status(4);
        if(orderInfoMapper.updateOrderInfo(orderInfo)==0){
            throw new OrderException("订单操作失败。",612);
        }

        List<OrderDetail> orderDetailList = orderDetailMapper.getAllOrderDetailByOrder_id(order_id);

        Sku sku;
        Product product;

        for(OrderDetail orderDetail : orderDetailList){
            sku = skuMapper.getSkuById(orderDetail.getSku_id());
            //sku被删除了
            if(null == sku)continue;
            product = productMapper.getProductById(orderDetail.getProduct_id());
            if(null == product)continue;
            //恢复库存
            sku.setStock(sku.getStock()+orderDetail.getNum());
            //恢复销量
            product.setSale(product.getSale()-orderDetail.getNum());
            skuMapper.updateSku(sku);
            productMapper.updateProduct(product);

        }
    }

    @Override
    public void updateOrderStatus(BigInteger order_id, int status) throws OrderException {
        OrderInfo orderInfo = orderInfoMapper.getOrderInfoById(order_id);
        orderInfo.setOrder_status(status);
        if(0 == orderInfoMapper.updateOrderInfo(orderInfo)){
            throw new OrderException("订单操作失败。",612);
        }
    }
}
