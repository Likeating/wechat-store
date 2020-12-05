package com.fortwelve.wechatstore.service;

import com.fortwelve.wechatstore.pojo.PropertyKey;

import java.math.BigInteger;
import java.util.List;

public interface PropertyKeyService {
     int addPropertyKey(PropertyKey propertyKey);
     int updatePropertyKey(PropertyKey propertyKey);
     int deletePropertyKeyById(BigInteger id);
     PropertyKey getPropertyKeyById(BigInteger id);
     List<PropertyKey> getAllPropertyKey();
}
