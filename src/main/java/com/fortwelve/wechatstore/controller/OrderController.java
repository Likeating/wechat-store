package com.fortwelve.wechatstore.controller;

import com.auth0.jwt.interfaces.Claim;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fortwelve.wechatstore.component.MsgMap;
import com.fortwelve.wechatstore.pojo.OrderDetail;
import com.fortwelve.wechatstore.pojo.OrderInfo;
import com.fortwelve.wechatstore.service.OrderService;
import com.fortwelve.wechatstore.util.JWTUtils;
import com.fortwelve.wechatstore.component.MyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
//@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/my/order")
public class OrderController {

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    OrderService orderService;
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Value("${JWTUtils.wx.signature}")
    private String wxSignature;
    @Value("${JWTUtils.wx.minute}")
    private int wxMinute;

    @PostMapping("/create")
    public Object create(String consignee_addr, String note, String details, HttpServletRequest request, HttpServletResponse response){
        MsgMap msg = new MsgMap();

        try{
            //获取token
            String token = request.getHeader("token");
            Map<String, Claim> tokenMap = JWTUtils.decode(token,wxSignature);
            String customerIdstr = tokenMap.get("userId").asString();

            if (null == consignee_addr || null == note || null == details || null == customerIdstr){
                msg.setMeta("请求不正确。",701);
                return msg;
            }
            //获取customerId
            BigInteger cutomerId = new BigInteger(customerIdstr);

            LinkedList<OrderDetail> list = objectMapper.readValue(details, new TypeReference<LinkedList<OrderDetail>>() {});

            if(list.size()==0){
                msg.setMeta("请求不正确。",701);
                return msg;
            }

            OrderInfo orderInfo = new OrderInfo();

            orderInfo.setCustomer_id(cutomerId);
            orderInfo.setFreight_price(new BigDecimal("0"));
            orderInfo.setAddress(consignee_addr);
            orderInfo.setNote(note);

            //创建订单
            orderService.createOrder(orderInfo,list);

            //加入检查缓存，防止超时未支付
            String id = String.valueOf(orderInfo.getOrder_id());
            stringRedisTemplate.opsForSet().add("check_order_id",id);
            stringRedisTemplate.opsForHash().put("check_order_hash",id,String.valueOf(orderInfo.getCreate_time().getTime()));

            msg.put("order_id",id);
            msg.put("user_id",orderInfo.getCustomer_id());
            msg.put("order_price",orderInfo.getPay_price());
            msg.put("consignee_addr",orderInfo.getAddress());
            msg.put("pay_status",orderInfo.getOrder_status());

            msg.setMeta("创建成功。",200);
        }catch (MyException e){
            msg.setMeta(e.getMessage(),e.getCode());
        }catch (Exception e){
            e.printStackTrace();
            msg.setMeta("服务器出错。",500);
        }
        return msg;
    }

    @PostMapping("/pay")
    public Object pay(String order_id,HttpServletRequest request){
        MsgMap msg = new MsgMap();
        try{
            //获取token
            String token = request.getHeader("token");
            Map<String, Claim> tokenMap = JWTUtils.decode(token,wxSignature);
            String customerIdstr = tokenMap.get("userId").asString();

            if (null == order_id || null == customerIdstr){
                msg.setMeta("请求不正确。",701);
                return msg;
            }
            //支付，这里直接在service层封装一个接口
            BigInteger id = new BigInteger(order_id);
            orderService.payOrderById(id,new BigInteger(customerIdstr));
            OrderInfo orderInfo = orderService.getOrderInfoById(id);

            //在未支付的缓存中将该记录删除
            stringRedisTemplate.opsForSet().remove("check_order_id",String.valueOf(id));
            stringRedisTemplate.opsForHash().delete("check_order_hash",String.valueOf(id));

            msg.put("orderInfo",orderInfo);
            msg.setMeta("支付成功。",200);
        }catch (MyException e){
            msg.setMeta(e.getMessage(),e.getCode());
        }catch (Exception e){
            e.printStackTrace();
            msg.setMeta("服务器出错。",500);
        }
        return msg;
    }
    @RequestMapping("/all")
    public Object all(Integer type ,Integer offset, Integer rows, HttpServletRequest request){
        MsgMap msg = new MsgMap();
        try{
            //获取token
            String token = request.getHeader("token");
            Map<String, Claim> tokenMap = JWTUtils.decode(token,wxSignature);
            String customerIdstr = tokenMap.get("userId").asString();
            if (null == customerIdstr || null == type){
                msg.setMeta("请求不正确。",701);
                return msg;
            }
            int status;
            switch (type){
                case 2:
                    status = 0;
                    break;
                case 3:
                    status = 1;
                    break;
                default:
                    status = -1;
            }
            List<OrderInfo> orderInfos = orderService.getAllOrderInfo(new BigInteger(customerIdstr),status,1,offset,rows);

            msg.put("orderInfos",orderInfos);
            msg.setMeta("查询成功。",200);
        }catch (Exception e){
            e.printStackTrace();
            msg.setMeta("服务器出错。",500);
        }
        return msg;
    }
    @RequestMapping("/checkOrder")
    public Object checkOrder(String order_id,HttpServletRequest request){
        MsgMap msg = new MsgMap();

        Map<String,Object> orderMap = new HashMap<>();
        LinkedList<Map<String,Object>> linkedList = new LinkedList<>();


        try{
            //获取token
            String token = request.getHeader("token");
            Map<String, Claim> tokenMap = JWTUtils.decode(token,wxSignature);

            if (null == order_id){
                msg.setMeta("请求不正确。",701);
                return msg;
            }

            OrderInfo orderInfo = orderService.getOrderInfoById(new BigInteger(order_id));

            //确保用户只能查看自己订单
            BigInteger customerId = new BigInteger(tokenMap.get("userId").asString());
            if(!orderInfo.getCustomer_id().equals(customerId)){
                msg.setMeta("无权操作订单。",611);
                return msg;
            }
            List<OrderDetail> orderDetails = orderService.getAllOrderDetailByOrder_id(new BigInteger(order_id));

            msg.put("orderInfo",orderInfo);
            msg.put("orderDetails",orderDetails);

            msg.setMeta("查询成功。",200);
        }catch (Exception e){
            e.printStackTrace();
            msg.setMeta("服务器出错。",500);
        }
        return msg;
    }
}
