package com.fortwelve.wechatstore.service;

import com.fortwelve.wechatstore.dao.PropertyKeyMapper;
import com.fortwelve.wechatstore.pojo.PropertyKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
public class PropertyKeyServiceImpl implements PropertyKeyService{

    @Autowired
    PropertyKeyMapper propertyKeyMapper;
    public int addPropertyKey(PropertyKey propertyKey){
        return propertyKeyMapper.addPropertyKey(propertyKey);
    }
    public int updatePropertyKey(PropertyKey propertyKey){
        return propertyKeyMapper.updatePropertyKey(propertyKey);
    }
    public int deletePropertyKeyById(BigInteger id){
        return propertyKeyMapper.deletePropertyKeyById(id);
    }
    public PropertyKey getPropertyKeyById(BigInteger id){
        return propertyKeyMapper.getPropertyKeyById(id);
    }
    public List<PropertyKey> getAllPropertyKey(){
        return propertyKeyMapper.getAllPropertyKey();
    }
}
