package com.fortwelve.wechatstore.controller;

import com.auth0.jwt.interfaces.Claim;
import com.fortwelve.wechatstore.component.MsgMap;
import com.fortwelve.wechatstore.pojo.Customer;
import com.fortwelve.wechatstore.pojo.OrderDetail;
import com.fortwelve.wechatstore.pojo.OrderInfo;
import com.fortwelve.wechatstore.pojo.Sku;
import com.fortwelve.wechatstore.service.CustomerService;
import com.fortwelve.wechatstore.service.OrderService;
import com.fortwelve.wechatstore.util.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/orderManage")
public class OrderManagerController {

    @Autowired
    OrderService orderService;

    @Autowired
    CustomerService customerService;

    @Value("${JWTUtils.manager.signature}")
    private String managerSignature;
    @Value("${JWTUtils.manager.minute}")
    private int managerMinute;

    @RequestMapping("/getOrders")
    public Object getOrders(BigInteger customer_id, Integer order_status, Integer sort, Integer pageSize, Integer currentPage, HttpServletRequest request){
        MsgMap msg = new MsgMap();
        try{
            //权限判断
            String token = request.getHeader("token");
            Map<String, Claim> tokenMap = JWTUtils.decode(token,managerSignature);
            String role = tokenMap.get("role").asString();
            if(!role.equals("超级管理员") && !role.equals("订单管理员")){
                msg.setMeta("没有权限操作。",611);
                return msg;
            }
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
    public Object getOrdersDetail(BigInteger order_id,HttpServletRequest request){
        MsgMap msg = new MsgMap();
        try{
            //权限判断
            String token = request.getHeader("token");
            Map<String, Claim> tokenMap = JWTUtils.decode(token,managerSignature);
            String role = tokenMap.get("role").asString();
            if(!role.equals("超级管理员") && !role.equals("订单管理员")){
                msg.setMeta("没有权限操作。",611);
                return msg;
            }

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
    public Object orderCommit(BigInteger order_id,HttpServletRequest request){
        MsgMap msg = new MsgMap();
        try{
            //权限判断
            String token = request.getHeader("token");
            Map<String, Claim> tokenMap = JWTUtils.decode(token,managerSignature);
            String role = tokenMap.get("role").asString();
            if(!role.equals("超级管理员") && !role.equals("订单管理员")){
                msg.setMeta("没有权限操作。",611);
                return msg;
            }

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