package com.fortwelve.wechatstore.service;

import com.fortwelve.wechatstore.pojo.Sku;

import java.math.BigInteger;
import java.util.List;

public interface SkuService {
     int addSku(Sku sku);
     int updateSku(Sku sku);
     int deleteSkuById(BigInteger id);
     Sku getSkuById(BigInteger id);
     List<Sku> getAllSku();
     List<Sku> getSkuByProductId(BigInteger product_id);
}
