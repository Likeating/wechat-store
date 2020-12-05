package com.fortwelve.wechatstore.service;

import com.fortwelve.wechatstore.dao.PropertyValueMapper;
import com.fortwelve.wechatstore.pojo.PropertyValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
public class PropertyValueServiceImpl implements PropertyValueService{
    @Autowired
    PropertyValueMapper propertyValueMapper;
    public int addPropertyValue(PropertyValue propertyValue){
        return propertyValueMapper.addPropertyValue(propertyValue);
    }
    public int updatePropertyValue(PropertyValue propertyValue){
        return propertyValueMapper.updatePropertyValue(propertyValue);
    }
    public int deletePropertyValueById(BigInteger id){
        return propertyValueMapper.deletePropertyValueById(id);
    }
    public PropertyValue getPropertyValueById(BigInteger id){
        return propertyValueMapper.getPropertyValueById(id);
    }
    public List<PropertyValue> getAllPropertyValue(){
        return propertyValueMapper.getAllPropertyValue();
    }
}
