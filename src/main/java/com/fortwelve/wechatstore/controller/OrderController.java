package com.fortwelve.wechatstore.controller;

import com.auth0.jwt.interfaces.Claim;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fortwelve.wechatstore.pojo.OrderDetail;
import com.fortwelve.wechatstore.pojo.OrderInfo;
import com.fortwelve.wechatstore.service.OrderService;
import com.fortwelve.wechatstore.util.JWTUtils;
import com.fortwelve.wechatstore.util.OrderException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;


@RestController
@RequestMapping("/my/order")
public class OrderController {

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    OrderService orderService;
    @Autowired
    JWTUtils jwtUtils;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostMapping("/create")
    public Object create(String consignee_addr,String note, String details, HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> map = new HashMap<>();
        Map<String,Object> msg = new HashMap<>();
        Map<String,Object> meta = new HashMap<>();

        try{
            LinkedList<OrderDetail> list = objectMapper.readValue(details, new TypeReference<LinkedList<OrderDetail>>() {});
//            System.out.println(list);

            logger.info("consignee_addr"+consignee_addr);
            logger.info("details"+list.get(1).toString());


            String token = request.getHeader("token");
            Map<String, Claim> tokenMap = jwtUtils.decode(token);
            BigInteger cutomerId = new BigInteger(tokenMap.get("userId").asString());

            OrderInfo orderInfo = new OrderInfo();

            orderInfo.setCustomer_id(cutomerId);
            orderInfo.setFreight_price(new BigDecimal("0"));
            orderInfo.setAddress(consignee_addr);
            orderInfo.setNote(note);

            //创建订单
            orderService.createOrder(orderInfo,list);

            msg.put("order_id",orderInfo.getOrder_id());
            msg.put("user_id",orderInfo.getCustomer_id());
            msg.put("order_price",orderInfo.getPay_price());
            msg.put("consignee_addr",orderInfo.getAddress());
            msg.put("pay_status",orderInfo.getOrder_status());

            meta.put("msg","创建成功。");
            meta.put("status",200);

            map.put("meta",meta);
            map.put("msg",msg);

        }catch (OrderException e){
            meta.put("msg",e.getMessage());
            meta.put("status",e.getCode());
            map.put("meta",meta);
        }catch (Exception e){
            e.printStackTrace();
            meta.put("msg","服务器出错。");
            meta.put("status",500);
            map.put("meta",meta);
        }
        return map;
    }
}
