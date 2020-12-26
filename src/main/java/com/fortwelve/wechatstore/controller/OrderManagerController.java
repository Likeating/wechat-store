package com.fortwelve.wechatstore.controller;

import com.fortwelve.wechatstore.component.MsgMap;
import com.fortwelve.wechatstore.pojo.Customer;
import com.fortwelve.wechatstore.pojo.OrderDetail;
import com.fortwelve.wechatstore.pojo.OrderInfo;
import com.fortwelve.wechatstore.pojo.Sku;
import com.fortwelve.wechatstore.service.CustomerService;
import com.fortwelve.wechatstore.service.OrderService;
import com.fortwelve.wechatstore.service.SkuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.plugin.javascript.navig.Array;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/order")
public class OrderManagerController {

    @Autowired
    OrderService orderService;

    @Autowired
    CustomerService customerService;

    @RequestMapping("/getOrders")
    public Object getOrders(BigInteger customer_id,Integer order_status,Integer sort,Integer pageSize, Integer currentPage){
        MsgMap msg = new MsgMap();
        try{

            int current=1;
            Integer head = null;
            if(pageSize != null){
                if(currentPage==null || currentPage<=0){
                    current=1;
                }else{
                    current=currentPage;
                }
                head=(pageSize*(current-1));
            }

            List<OrderInfo> orderInfoList=orderService.getAllOrderInfo(customer_id,order_status,sort,head,pageSize);//
                msg.put("currentPage",current);
            msg.put("total",orderInfoList.size());
            msg.put("orderInfoList",orderInfoList);
            msg.setMeta("查询成功。",200);
        }catch (Exception e){
            e.printStackTrace();
            msg.setMeta("服务器出错。",500);
        }
        return msg;
    }

    @RequestMapping("/getOrdersDetail")
    public Object getOrdersDetail(BigInteger order_id){
        MsgMap msg = new MsgMap();
        try{
            OrderInfo orderInfo=orderService.getOrderInfoById(order_id);
            msg.put("orderInfo",orderInfo);
            List<OrderDetail> orderDetail=orderService.getAllOrderDetailByOrder_id(order_id);
            msg.put("orderDetails",orderDetail);
            msg.setMeta("查询成功。",200);
        }catch (Exception e){
            e.printStackTrace();
            msg.setMeta("服务器出错。",500);
        }
        return msg;
    }

    @PostMapping("/orderCommit")
    public Object orderCommit(BigInteger order_id){
        MsgMap msg = new MsgMap();
        try{
            int status=2;
            orderService.updateOrderStatus(order_id,status);

            msg.setMeta("操作成功。",200);
        }catch (Exception e){
            e.printStackTrace();
            msg.setMeta("服务器出错。",500);
        }
        return msg;
    }
}