package com.fortwelve.wechatstore.util;

import com.fortwelve.wechatstore.pojo.OrderInfo;
import com.fortwelve.wechatstore.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

@Component
public class OrderPolice extends Thread{

    @Autowired
    private OrderService orderService;
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    /**
     * 订单检查线程
     */
    @Override
    public void run() {
        Logger logger = LoggerFactory.getLogger(this.getClass());


        try {
            SetOperations<String,String> opsForSet = stringRedisTemplate.opsForSet();
            HashOperations<String,String,String> opsForHash = stringRedisTemplate.opsForHash();
            List<OrderInfo> orderInfoList = orderService.
                    getAllOrderInfoByCustomer_idAndOrder_status(new BigInteger("1"),0,1);

            logger.info("数据库未支付订单数量："+orderInfoList.size());
            String id;
            for(OrderInfo orderInfo:orderInfoList){
                id = String.valueOf(orderInfo.getOrder_id());
                opsForSet.add("check_order_id",id);
                opsForHash.put("check_order_hash",id,String.valueOf(orderInfo.getCreate_time().getTime()));
            }
//            for(int j=0; j<10000;j++){
//                opsForSet.add("test",String.valueOf(Math.random()));
//            }
            ScanOptions scanOptions = ScanOptions.scanOptions().match("*").count(100).build();

            Cursor<String> cursor2 = stringRedisTemplate.opsForSet().scan("test", scanOptions);
            stringRedisTemplate.multi();
            int i=0;
            while (cursor2.hasNext()){
                cursor2.next();
                i++;
            }
            logger.info("一共取出："+i+"下一次迭代。");
            cursor2.close();

            logger.info("订单检查线程初始化完成。");
        }catch (Exception e){
            logger.error("订单检查线程初始化出错："+e.getMessage());
            e.printStackTrace();
        }
        while(true){
            try {
                ScanOptions scanOptions = ScanOptions.scanOptions().match("*").count(2).build();
                Cursor<Map.Entry<Object,Object>> cursor = stringRedisTemplate.opsForHash().scan("check_order_hash",
                        scanOptions);

                int i=0;
                while (cursor.hasNext()){
                    Map.Entry<Object,Object> entry = cursor.next();
                    String key = (String)entry.getKey();
                    String value = (String)entry.getValue();

//                    logger.info("cursorId:"+String.valueOf(cursor.getCursorId()));
//                    logger.info("key:"+key+"value:"+value);
                    i++;
                }
                logger.info("一共取出："+i+"下一次迭代。");
                cursor.close();
                Thread.sleep(30000);
            }catch (Exception e){
                logger.error("订单检查线程出错："+e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
