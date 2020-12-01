package com.fortwelve.wechatstore.service;

import com.fortwelve.wechatstore.mapper.SkuMapper;
import com.fortwelve.wechatstore.pojo.Sku;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
public class SkuService {
    @Autowired
    SkuMapper skuMapper;
    public int addSku(Sku sku){
        return skuMapper.addSku(sku);
    }
    public int updateSku(Sku sku){
        return skuMapper.updateSku(sku);
    }
    public int deleteSkuById(BigInteger id){
        return skuMapper.deleteSkuById(id);
    }
    public Sku getSkuById(BigInteger id){
        return skuMapper.getSkuById(id);
    }
    public List<Sku> getAllSku(){
        return skuMapper.getAllSku();
    }
}