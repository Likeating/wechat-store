package com.fortwelve.wechatstore.service.Impl;

import com.fortwelve.wechatstore.dao.StatisticMapper;
import com.fortwelve.wechatstore.pojo.Turnover;
import com.fortwelve.wechatstore.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.sql.Timestamp;

@Service
public class StatisticServiceImpl implements StatisticService {
    @Autowired
    StatisticMapper statisticMapper;
    @Override
    public Turnover getTurnover(BigInteger product_id, Timestamp start_time, Timestamp end_time) {
        return statisticMapper.getTurnover(product_id,start_time,end_time);
    }
}
