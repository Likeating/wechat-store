package com.fortwelve.wechatstore.service.Impl;

import com.fortwelve.wechatstore.dao.PropertyKeyMapper;
import com.fortwelve.wechatstore.dao.PropertyValueMapper;
import com.fortwelve.wechatstore.dao.SkuMapper;
import com.fortwelve.wechatstore.pojo.PropertyKey;
import com.fortwelve.wechatstore.pojo.PropertyValue;
import com.fortwelve.wechatstore.pojo.Sku;
import com.fortwelve.wechatstore.service.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service("skuService")
public class SkuServiceImpl implements SkuService {

    @Autowired
    SkuMapper skuMapper;

    @Autowired
    PropertyKeyMapper propertyKeyMapper;

    @Autowired
    PropertyValueMapper propertyValueMapper;

    @Override
    public int addSku(Sku sku) {
        return skuMapper.addSku(sku);
    }

    @Override
    public int updateSku(Sku sku) {
        return skuMapper.updateSku(sku);
    }

    @Override
    public int deleteSkuById(BigInteger id) {
        return skuMapper.deleteSkuById(id);
    }

    @Override
    public Sku getSkuById(BigInteger id) {
        return skuMapper.getSkuById(id);
    }

    @Override
    public List<Sku> getAllSku() {
        return skuMapper.getAllSku();
    }

    @Override
    public List<Sku> getSkuByProductId(BigInteger product_id) {
        return skuMapper.getSkuByProductId(product_id);
    }

    @Override
    public int getStockByProductId(BigInteger product_id) {
        return skuMapper.getStockByProductId(product_id);
    }

    @Override
    public int addPropertyKey(PropertyKey propertyKey) {
        return propertyKeyMapper.addPropertyKey(propertyKey);
    }

    @Override
    public int updatePropertyKey(PropertyKey propertyKey) {
        return propertyKeyMapper.updatePropertyKey(propertyKey);
    }

    @Override
    public int deletePropertyKeyById(BigInteger id) {
        return propertyKeyMapper.deletePropertyKeyById(id);
    }

    @Override
    public PropertyKey getPropertyKeyById(BigInteger id) {
        return propertyKeyMapper.getPropertyKeyById(id);
    }

    @Override
    public List<PropertyKey> getAllPropertyKey() {
        return propertyKeyMapper.getAllPropertyKey();
    }

    @Override
    public int addPropertyValue(PropertyValue propertyValue) {
        return propertyValueMapper.addPropertyValue(propertyValue);
    }

    @Override
    public int updatePropertyValue(PropertyValue propertyValue) {
        return propertyValueMapper.updatePropertyValue(propertyValue);
    }

    @Override
    public int deletePropertyValueById(BigInteger id) {
        return propertyValueMapper.deletePropertyValueById(id);
    }

    @Override
    public PropertyValue getPropertyValueById(BigInteger id) {
        return propertyValueMapper.getPropertyValueById(id);
    }

    @Override
    public List<PropertyValue> getAllPropertyValue() {
        return propertyValueMapper.getAllPropertyValue();
    }
}
