package com.fortwelve.wechatstore.controller;

import com.fortwelve.wechatstore.component.MsgMap;
import com.fortwelve.wechatstore.pojo.Turnover;
import com.fortwelve.wechatstore.service.StatisticService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.sql.Timestamp;

@Slf4j
@RestController
@RequestMapping("/statistic")
public class StatisticController {

//    @Value("${JWTUtils.manager.signature}")
//    private String managerSignature;
//    @Value("${JWTUtils.manager.minute}")
//    private int managerMinute;

    @Autowired
    StatisticService statisticService;

    @GetMapping("/getTurnover")
    public Object getCustomers(BigInteger product_id, Long start_time,Long end_time){
        MsgMap msg = new MsgMap();
        try{

            Turnover turnover = statisticService.getTurnover(product_id,null== start_time?null:new Timestamp(start_time),null== end_time?null:new Timestamp(end_time));
            msg.put("turnover",turnover);

            msg.setMeta("查询成功。",200);
        }catch (Exception e){
            e.printStackTrace();
            log.debug("/statistic/getTurnover出错："+e.getMessage());
            msg.setMeta("服务器出错。",500);
        }
        return msg;
    }



}
