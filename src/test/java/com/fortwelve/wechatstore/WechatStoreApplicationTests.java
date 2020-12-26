package com.fortwelve.wechatstore;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fortwelve.wechatstore.component.MsgMap;
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

    @Test
    void test01() throws JsonProcessingException {
//        System.out.println(orderService.getAllOrderInfo(null, null, null));
        ProductDTO productDTO = objectMapper.readValue("{\"msg\":{\"product\":{\"product_id\":13,\"product_name\":\"null\",\"category_id\":1,\"price\":100.00,\"add_time\":\"2020-11-30T14:50:31.000+00:00\",\"delete_time\":null,\"state\":\"秋季上衣\",\"sale\":250,\"picture_main\":{\"picture_id\":9,\"url\":\"https://g-search1.alicdn.com/img/bao/uploaded/i4/i4/2273198458/O1CN01rPeKwC2CLoY09QBjU_!!2273198458-0-lubanu-s.jpg_580x580Q90.jpg_.webp\"},\"picture_preview\":[{\"picture_id\":1,\"url\":\"http://g-search1.alicdn.com/img/bao/uploaded/i4/imgextra/i4/118310905/O1CN01l8cHu21IYX4s1qva1_!!0-saturn_solar.jpg_580x580Q90.jpg_.webp\"},{\"picture_id\":2,\"url\":\"https://g-search3.alicdn.com/img/bao/uploaded/i4/i1/199080219/O1CN01BtIaPb1DULMgcfeCK_!!199080219.jpg_580x580Q90.jpg_.webp\"},{\"picture_id\":3,\"url\":\"https://g-search1.alicdn.com/img/bao/uploaded/i4/i1/2139796914/O1CN01e7VyL020wf29UhFKr_!!2139796914-0-picasso.jpg_580x580Q90.jpg_.webp\"}],\"picture_detail\":[{\"picture_id\":1,\"url\":\"http://g-search1.alicdn.com/img/bao/uploaded/i4/imgextra/i4/118310905/O1CN01l8cHu21IYX4s1qva1_!!0-saturn_solar.jpg_580x580Q90.jpg_.webp\"},{\"picture_id\":2,\"url\":\"https://g-search3.alicdn.com/img/bao/uploaded/i4/i1/199080219/O1CN01BtIaPb1DULMgcfeCK_!!199080219.jpg_580x580Q90.jpg_.webp\"},{\"picture_id\":3,\"url\":\"https://g-search1.alicdn.com/img/bao/uploaded/i4/i1/2139796914/O1CN01e7VyL020wf29UhFKr_!!2139796914-0-picasso.jpg_580x580Q90.jpg_.webp\"}],\"keys\":{\"15\":\"尺寸\",\"16\":\"颜色\"},\"values\":{\"29\":\"X\",\"30\":\"L\",\"31\":\"红色\",\"32\":\"蓝色\"},\"sku_list\":[{\"sku_id\":33,\"product_id\":13,\"properties\":\"15:29_16:31\",\"sku_price\":86.00,\"stock\":99,\"picture_id\":21},{\"sku_id\":34,\"product_id\":13,\"properties\":\"15:30_16:32\",\"sku_price\":87.00,\"stock\":0,\"picture_id\":22},{\"sku_id\":35,\"product_id\":13,\"properties\":\"15:29_16:32\",\"sku_price\":88.00,\"stock\":82,\"picture_id\":23},{\"sku_id\":36,\"product_id\":13,\"properties\":\"15:30_16:31\",\"sku_price\":89.00,\"stock\":99,\"picture_id\":24}]}},\"meta\":{\"msg\":\"获取成功。\",\"status\":200}}", ProductDTO.class);
        System.out.println(ProductUtils.getProductByProductDTO(productDTO));
        Product product = new Product();
        product.setSale(null);
        System.out.println(productDTO);
    }

}
