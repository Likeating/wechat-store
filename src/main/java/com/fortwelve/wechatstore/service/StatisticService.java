package com.fortwelve.wechatstore.service;

import com.fortwelve.wechatstore.pojo.Turnover;

import java.math.BigInteger;
import java.sql.Timestamp;

public interface StatisticService {
    /**
     * 获取营业额
     * @param product_id 如果需要查某个商品，请填写商品id，如果想要查所有商品，请设置为null
     * @param start_time 开始时间，如果不提供，表示不限制开始时间
     * @param end_time 结束时间，不提供则表示不限制结束时间，即到现在为止
     * @return
     */
    Turnover getTurnover(BigInteger product_id, Timestamp start_time, Timestamp end_time);
}
