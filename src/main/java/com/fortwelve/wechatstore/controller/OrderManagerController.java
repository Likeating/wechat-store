package com.fortwelve.wechatstore.controller;

import com.fortwelve.wechatstore.component.MsgMap;
import com.fortwelve.wechatstore.pojo.Customer;
import com.fortwelve.wechatstore.pojo.OrderDetail;
import com.fortwelve.wechatstore.pojo.OrderInfo;
import com.fortwelve.wechatstore.pojo.Sku;
import com.fortwelve.wechatstore.service.CustomerService;
import com.fortwelve.wechatstore.service.OrderService;
import com.fortwelve.wechatstore.service.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import sun.plugin.javascript.navig.Array;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RequestMapping("/order")
public class OrderManagerController {

    @Autowired
    OrderService orderService;
    @Autowired
    CustomerService customerService;
    @Autowired
    SkuService skuService;

    @RequestMapping("/getOrders")
    public Object getOrders(Integer pageSize,Integer currentPage,Array key){
        MsgMap msg = new MsgMap();
        Map<String,Object> map;
        List<Object> list = new LinkedList<>();
        try{
            List<OrderInfo> orderInfoList;
            if(pageSize==null){
                orderInfoList=orderService.getAllOrderInfo();
            }else {
                int current;
                if(currentPage==null || currentPage<0){
                    current=1;
                }else{
                    current=currentPage;
                }
                int head=(pageSize*(current-1));
                orderInfoList=orderService.getAllOrderInfo();
                //msg.put("currentPage",current);
            }
            for(int i=0;i<orderInfoList.size();i++){
                map = new HashMap<>();
                Customer customer=customerService.getCustomerById(orderInfoList.get(i).getCustomer_id());
                map.put("order_id",orderInfoList.get(i).getOrder_id());
                map.put("create_time",orderInfoList.get(i).getCreate_time());
                map.put("customer_id",orderInfoList.get(i).getCustomer_id());
                map.put("customer_name",customer.getNickName());
                map.put("order_status",orderInfoList.get(i).getOrder_status());
                map.put("address",orderInfoList.get(i).getAddress());
                map.put("total_price",orderInfoList.get(i).getTotal_price());
                list.add(map);
            }

            msg.put("total",orderInfoList.size());
            msg.put("list",list);
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
        List<Object> list = new LinkedList<>();
        Map<String,Object> map;
        try{
            OrderInfo orderInfo=orderService.getOrderInfoById(order_id);
            Customer customer=customerService.getCustomerById(orderInfo.getCustomer_id());
            msg.put("order_id",orderInfo.getOrder_id());
            msg.put("create_time",orderInfo.getCreate_time());
            msg.put("customer_id",orderInfo.getCustomer_id());
            msg.put("customer_name",customer.getNickName());
            msg.put("total_price",orderInfo.getTotal_price());
            msg.put("order_status",orderInfo.getOrder_status());
            msg.put("address",orderInfo.getAddress());
            List<OrderDetail> orderDetail=orderService.getAllOrderDetailByOrder_id(order_id);
            String []properties;
            String []kv;
            String color="";
            String size="";
            for(int i=0;i<orderDetail.size();i++){
                Sku sku=skuService.getSkuById(orderDetail.get(i).getSku_id());
                properties=sku.getProperties().split("_");
                for(String property:properties){
                    kv=property.split(":");
                    if(skuService.getPropertyKeyById(BigInteger.valueOf(Long.parseLong(kv[0]))).getKey_name()=="颜色"){
                        color=skuService.getPropertyValueById(BigInteger.valueOf(Long.parseLong(kv[1]))).getValue_name();
                    }
                    if(skuService.getPropertyKeyById(BigInteger.valueOf(Long.parseLong(kv[0]))).getKey_name()=="尺寸"){
                        size=skuService.getPropertyValueById(BigInteger.valueOf(Long.parseLong(kv[1]))).getValue_name();
                    }
                }
                map = new HashMap<>();
                map.put("order_id",orderDetail.get(i).getId());
                map.put("product_name",orderDetail.get(i).getProduct_name());
                map.put("sku_id",orderDetail.get(i).getSku_id());
                map.put("color",color);
                map.put("size",size);
                map.put("num",orderDetail.get(i).getNum());
                map.put("sku_price",orderDetail.get(i).getSku_price());
                map.put("total",orderDetail.get(i).getNum()*orderDetail.get(i).getSku_price().intValue());
                list.add(map);
            }
            msg.put("orders",list);
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
            OrderInfo orderInfo=orderService.getOrderInfoById(order_id);
            orderInfo.setOrder_status(2);
            if(orderService.updateOrderInfo(orderInfo)==0){
                msg.setMeta("修改状态失败。",500);
                return msg;
            }

            msg.setMeta("操作成功。",200);
        }catch (Exception e){
            e.printStackTrace();
            msg.setMeta("服务器出错。",500);
        }
        return msg;
    }
}
