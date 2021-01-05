package com.fortwelve.wechatstore.service;

import com.fortwelve.wechatstore.pojo.PropertyKey;
import com.fortwelve.wechatstore.pojo.PropertyValue;

import java.math.BigInteger;
import java.util.List;

public interface PropertyService {
    int addPropertyKey(PropertyKey propertyKey);

    int updatePropertyKey(PropertyKey propertyKey);

    int deletePropertyKeyById(BigInteger id);

    PropertyKey getPropertyKeyById(BigInteger id);

    List<PropertyKey> getAllPropertyKey();

    //value

    int addPropertyValue(PropertyValue propertyValue);

    int updatePropertyValue(PropertyValue propertyValue);

    int deletePropertyValueById(BigInteger id);

    PropertyValue getPropertyValueById(BigInteger id);

    List<PropertyValue> getAllPropertyValue();
}
