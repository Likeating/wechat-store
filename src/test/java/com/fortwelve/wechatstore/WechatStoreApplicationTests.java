package com.fortwelve.wechatstore;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fortwelve.wechatstore.component.MsgMap;
import com.fortwelve.wechatstore.dao.StatisticMapper;
import com.fortwelve.wechatstore.dto.ProductDTO;
import com.fortwelve.wechatstore.dto.ProductPropertiesDTO;
import com.fortwelve.wechatstore.pojo.OrderDetail;
import com.fortwelve.wechatstore.pojo.Picture;
import com.fortwelve.wechatstore.pojo.PictureList;
import com.fortwelve.wechatstore.pojo.Product;
import com.fortwelve.wechatstore.service.OrderService;
import com.fortwelve.wechatstore.service.ProductService;
import com.fortwelve.wechatstore.component.OrderCheckerThread;
import com.fortwelve.wechatstore.util.ProductUtils;
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
    @Autowired
    StatisticMapper statisticMapper;
    @Test
    void test01() throws JsonProcessingException {
        System.out.println(statisticMapper.getTurnover(null, null, Timestamp.valueOf("2020-12-26 16:35:06")));
        System.out.println(statisticMapper.getTurnover(null, Timestamp.valueOf("2020-12-26 16:35:06"),null));
        System.out.println(statisticMapper.getTurnover(null,  Timestamp.valueOf("2020-12-26 16:35:06"),Timestamp.valueOf("2020-12-26 16:37:10")));

    }

}
