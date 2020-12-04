package com.fortwelve.wechatstore.service;

import com.fortwelve.wechatstore.dao.PictureListMapper;
import com.fortwelve.wechatstore.pojo.PictureList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
public class PictureListService {
    @Autowired
    PictureListMapper pictureListMapper;

    public int addProductDetail(PictureList pictureList){
        return pictureListMapper.addProductDetail(pictureList);
    }

    public int deleteProductDetailById(BigInteger id){
        return pictureListMapper.deleteProductDetailById(id);
    }

    public PictureList getProductDetailById(BigInteger id){
        return pictureListMapper.getProductDetailById(id);
    }

    public List<PictureList> getAllProductDetail(){
        return pictureListMapper.getAllProductDetail();
    }

    public int updateProductDetail(PictureList pictureList){
        return pictureListMapper.updateProductDetail(pictureList);
    }


}
