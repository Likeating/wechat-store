package com.fortwelve.wechatstore.component;

import com.fortwelve.wechatstore.pojo.OrderInfo;
import com.fortwelve.wechatstore.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;


@Slf4j
@Component
public class OrderCheckerThread extends Thread{

    @Autowired
    OrderService orderService;
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Value("${OrderCheck.unpaid.TTL}")
    int orderTTL;
    @Value("${OrderCheck.unpaid.interval}")
    int interval;

    /**
     * 订单检查线程
     */
    @Override
    public void run() {
        // 初始化，查找数据库中未支付订单，并存在redis中
        try {
            SetOperations<String,String> opsForSet = stringRedisTemplate.opsForSet();
            HashOperations<String,String,String> opsForHash = stringRedisTemplate.opsForHash();

            List<OrderInfo> orderInfoList = orderService.
                    getAllOrderInfo(new BigInteger("1"),0,1);

            log.info("数据库未支付订单数量："+orderInfoList.size());

            for(OrderInfo orderInfo:orderInfoList){
                String id = String.valueOf(orderInfo.getOrder_id());
                opsForSet.add("check_order_id",id);
                opsForHash.put("check_order_hash",id,String.valueOf(orderInfo.getCreate_time().getTime()));
            }
            log.info("订单检查线程初始化完成。");
            //循环检查
            int ttl=orderTTL*1000;
            while(true){
                log.info("开始订单检查。");
                try {
                    ScanOptions scanOptions = ScanOptions.scanOptions().count(2).build();
                    Cursor<String> cursor = opsForSet.scan("check_order_id",scanOptions);
                    Long currentTimeMillis = System.currentTimeMillis();
                    String id,createTime;
                    while (cursor.hasNext()){
                        id = cursor.next();
                        createTime = opsForHash.get("check_order_hash",id);

                        //未超时 跳过
                        if(currentTimeMillis-Long.parseLong(createTime) <= ttl)continue;

                        //超时
                        OrderInfo orderInfo = orderService.getOrderInfoById(new BigInteger(id));
                        if(orderInfo == null || orderInfo.getOrder_status() != 0){
                            //订单不存在 或 订单存在但状态不是未支付 移除
                            opsForSet.remove("check_order_id",id);
                            opsForHash.delete("check_order_hash",id);
                        }else if(orderInfo.getOrder_status() == 0){
                            //再次确认是未支付，防止状态已经改变了
                            //关闭订单
                            orderService.closeOrderById(orderInfo.getOrder_id());
                            //移除
                            opsForSet.remove("check_order_id",id);
                            opsForHash.delete("check_order_hash",id);
                            log.info("订单ID为"+id+"长时间支付，已将其关闭。");

                        }
                    }
                    cursor.close();
                    Thread.sleep(interval);//一分钟检查一次
                }catch (Exception e){
                    log.error("订单检查线程出错："+e.getMessage());
                    e.printStackTrace();
                }
            }
        }catch (Exception e){
            log.error("订单检查线程初始化出错："+e.getMessage());
            e.printStackTrace();
        }

    }
}
