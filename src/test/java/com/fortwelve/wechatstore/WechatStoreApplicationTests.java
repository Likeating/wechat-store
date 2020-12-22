package com.fortwelve.wechatstore;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fortwelve.wechatstore.component.MsgMap;
import com.fortwelve.wechatstore.pojo.OrderDetail;
import com.fortwelve.wechatstore.pojo.Product;
import com.fortwelve.wechatstore.service.OrderService;
import com.fortwelve.wechatstore.service.ProductService;
import com.fortwelve.wechatstore.component.OrderCheckerThread;
import com.fortwelve.wechatstore.util.WXapi;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.DigestUtils;

import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

@SpringBootTest
@Slf4j
class WechatStoreApplicationTests {



    @Autowired
    ProductService productService;
    @Autowired
    private WXapi wxapi;
    @Autowired
    OrderService orderService;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    RedisTemplate<String,String> redisTemplate;
    @Autowired
    MsgMap msgMap;
    @Autowired
    OrderCheckerThread orderCheckerThread;

    @Test
    void testProduct(){
//        Timestamp timestamp = Timestamp.valueOf("2020-11-30 17:57:33");
        Calendar calendar = Calendar.getInstance();
        System.out.println(calendar.getTime());
        Product product = productService.getProductById(BigInteger.valueOf(1));
        System.out.println(product);
        product.setAdd_time(new Timestamp(calendar.getTimeInMillis()));
        productService.addProduct(product);
        System.out.println(product);
    }



    @Test
    void Test01() throws IOException, NoSuchAlgorithmException {
        String md5Str = DigestUtils.md5DigestAsHex("中国".getBytes("ISO-8859-1"));
        System.out.println(md5Str);
    }
}
