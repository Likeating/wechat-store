package com.fortwelve.wechatstore.service;

import com.fortwelve.wechatstore.pojo.Picture;
import com.fortwelve.wechatstore.pojo.PictureList;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;

public interface PictureService {

    int addPicture(Picture picture);

    int deletePictureById(BigInteger id);

    Picture getPictureById(BigInteger id);

    List<Picture> getAllPicture();

    int updatePicture(Picture picture);


    public int addPictureList(PictureList pictureList);

    public int deletePictureListById(BigInteger id);

    public PictureList getPictureListById(BigInteger id);

    public List<PictureList> getAllPictureList();

    public int updatePictureList(PictureList pictureList);

    /**
     * 获取图片列表
     * @param id
     * @return
     */
    LinkedList<Picture> getPicturesById(BigInteger id);
}
