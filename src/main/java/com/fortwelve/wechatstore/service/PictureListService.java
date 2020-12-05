package com.fortwelve.wechatstore.service;

import com.fortwelve.wechatstore.pojo.PictureList;

import java.math.BigInteger;
import java.util.List;

public interface PictureListService {

    int addProductDetail(PictureList pictureList);
    int deleteProductDetailById(BigInteger id);
    PictureList getProductDetailById(BigInteger id);
    List<PictureList> getAllProductDetail();
    int updateProductDetail(PictureList pictureList);
}
