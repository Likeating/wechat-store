package com.fortwelve.wechatstore.service;

import com.fortwelve.wechatstore.pojo.Picture;

import java.math.BigInteger;
import java.util.List;

public interface PictureService {

    int addPicture(Picture picture);

    int deletePictureById(BigInteger id);

    Picture getPictureById(BigInteger id);

    List<Picture> getAllPicture();

    int updatePicture(Picture picture);
}
