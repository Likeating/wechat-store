package com.fortwelve.wechatstore;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fortwelve.wechatstore.dto.Detail;
import com.fortwelve.wechatstore.dto.UserInfo;
import com.fortwelve.wechatstore.pojo.Category;
import com.fortwelve.wechatstore.pojo.OrderDetail;
import com.fortwelve.wechatstore.pojo.Product;
import com.fortwelve.wechatstore.service.CustomerService;
import com.fortwelve.wechatstore.service.OrderService;
import com.fortwelve.wechatstore.service.ProductService;
import com.fortwelve.wechatstore.util.WXapi;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@SpringBootTest
class WechatStoreApplicationTests {



    @Autowired
    ProductService productService;
    @Autowired
    private WXapi wxapi;
    @Autowired
    OrderService orderService;
    @Autowired
    ObjectMapper objectMapper;

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
//        String rawData = "{\"nickName\":\"Jason\",\"gender\":1,\"language\":\"zh_CN\",\"city\":\"\",\"province\":\"Van\",\"country\":\"Turkey\",\"avatarUrl\":\"https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKolFX0XdToWC96ehuMmVL3MC5Tic4iaDFicJCHF9Q47uw60ibwUHppkuF45nbKhS7kAfeUp1xEVtp4KA/132\"}";
//        rawData=rawData+"a9xluJbwVSx6EjuxZEbTqw==";
//        System.out.println(wxapi.getSHA1("{\"nickName\":\"Band\",\"gender\":1,\"language\":\"zh_CN\",\"city\":\"Guangzhou\",\"province\":\"Guangdong\",\"country\":\"CN\",\"avatarUrl\":\"http://wx.qlogo.cn/mmopen/vi_32/1vZvI39NWFQ9XM4LtQpFrQJ1xlgZxx3w7bQxKARol6503Iuswjjn6nIGBiaycAjAtpujxyzYsrztuuICqIM5ibXQ/0\"}HyVFkGl5F5OQWJZZaNzBBg=="));
//        //System.out.println(wxApi.Code2Session("081Thkml22md964TMMkl2iuAvw4ThkmG"));
//        //System.out.println(objectMapper.readValue("{\"errcode\":40163,\"errmsg\":\"code been used, hints: [ req_id: 6heFeKqge-A0M3HA ]\"}".getBytes(), Code2Session.class));
//        orderService.createOrder(null,null);

        //OrderDetail orderDetail = new OrderDetail(BigInteger.valueOf(1),BigInteger.valueOf(1),BigInteger.valueOf(1),"", BigDecimal.valueOf(55),2);
//        List<OrderDetail> list = new LinkedList<>();
//        list.add(orderDetail);
//        list.add(orderDetail);
//        list.add(orderDetail);
        //[{"category_id":1,"category_name":"234"},{"category_id":1,"category_name":"234"},{"category_id":1,"category_name":"234"}]
        //[{"sku_id":1,"sku_price":55,"num":2},{"sku_id":1,"sku_price":55,"num":2},{"sku_id":1,"sku_price":55,"num":2}]
//        System.out.println(objectMapper.writeValueAsString(list));
//
//        JavaType javatype = objectMapper.getTypeFactory().constructType(List.class, Detail.class);
//        List<OrderDetail> list2 = objectMapper.readValue("[{\"sku_id\":1,\"sku_price\":55,\"num\":2},{\"sku_id\":1,\"sku_price\":55,\"num\":2},{\"sku_id\":1,\"sku_price\":55,\"num\":2}]",javatype);
//        System.out.println(list2);
//
//        List<OrderDetail> list3 = objectMapper.convertValue(list2, new TypeReference<List<OrderDetail>>() {
//        });
//
//        System.out.println((OrderDetail)list3.get(1));
        List<OrderDetail> list4 = objectMapper.readValue("[{\"sku_id\":1,\"sku_price\":55,\"num\":2},{\"sku_id\":1,\"sku_price\":55,\"num\":2},{\"sku_id\":1,\"sku_price\":55,\"num\":2}]",new TypeReference<List<OrderDetail>>() {
        });
        System.out.println(list4.get(1));
        BigDecimal bigDecimal = new BigDecimal("");
        BigDecimal bigDecimal2 = new BigDecimal("1");
        BigDecimal bigDecimal1;
        bigDecimal1 =bigDecimal;
        bigDecimal1.add(bigDecimal2);
        System.out.println(bigDecimal1.add(bigDecimal2));
        System.out.println(bigDecimal);
    }
}
