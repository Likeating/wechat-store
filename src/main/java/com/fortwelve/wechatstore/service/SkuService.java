package com.fortwelve.wechatstore.service;

import com.fortwelve.wechatstore.pojo.PropertyKey;
import com.fortwelve.wechatstore.pojo.PropertyValue;
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
    int getStockByProductId(BigInteger product_id);

    int addPropertyKey(PropertyKey propertyKey);
    int updatePropertyKey(PropertyKey propertyKey);
    int deletePropertyKeyById(BigInteger id);
    PropertyKey getPropertyKeyById(BigInteger id);
    List<PropertyKey> getAllPropertyKey();

    int addPropertyValue(PropertyValue propertyValue);
    int updatePropertyValue(PropertyValue propertyValue);
    int deletePropertyValueById(BigInteger id);
    PropertyValue getPropertyValueById(BigInteger id);
    List<PropertyValue> getAllPropertyValue();
}
