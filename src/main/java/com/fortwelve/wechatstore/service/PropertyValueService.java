package com.fortwelve.wechatstore.service;

import com.fortwelve.wechatstore.pojo.PropertyValue;

import java.math.BigInteger;
import java.util.List;

public interface PropertyValueService {
     int addPropertyValue(PropertyValue propertyValue);
     int updatePropertyValue(PropertyValue propertyValue);
     int deletePropertyValueById(BigInteger id);
     PropertyValue getPropertyValueById(BigInteger id);
     List<PropertyValue> getAllPropertyValue();
}
