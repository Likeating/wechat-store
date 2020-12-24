package com.fortwelve.wechatstore.service.Impl;

import com.fortwelve.wechatstore.dao.*;
import com.fortwelve.wechatstore.pojo.Picture;
import com.fortwelve.wechatstore.pojo.PictureList;
import com.fortwelve.wechatstore.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;

@Service
public class PictureServiceImpl implements PictureService {
    @Autowired
    PictureMapper pictureMapper;
    @Autowired
    PictureListMapper pictureListMapper;

    @Override
    public int addPicture(Picture picture) {
        return pictureMapper.addPicture(picture);
    }

    @Override
    public int deletePictureById(BigInteger id) {
        return pictureMapper.deletePictureById(id);
    }

    @Override
    public Picture getPictureById(BigInteger id) {
        return pictureMapper.getPictureById(id);
    }

    @Override
    public List<Picture> getAllPicture() {
        return pictureMapper.getAllPicture();
    }

    @Override
    public int updatePicture(Picture picture) {
        return pictureMapper.updatePicture(picture);
    }


    @Override
    public int addPictureList(PictureList pictureList) {
        return pictureListMapper.addPictureList(pictureList);
    }

    @Override
    public int deletePictureListById(BigInteger id) {
        return pictureListMapper.deletePictureListById(id);
    }

    @Override
    public PictureList getPictureListById(BigInteger id) {
        return pictureListMapper.getPictureListById(id);
    }

    @Override
    public List<PictureList> getAllPictureList() {
        return pictureListMapper.getAllPictureList();
    }

    @Override
    public int updatePictureList(PictureList pictureList) {
        return pictureListMapper.updatePictureList(pictureList);
    }

    @Override
    public LinkedList<Picture> getPicturesById(BigInteger id) {
        //获取图集
        PictureList pictureList = pictureListMapper.getPictureListById(id);
        LinkedList<Picture> pictureUrlList = new LinkedList<>();
        if(null == pictureList || null == pictureList.getPictures_id()){
            return null;
        }
        String [] pictureIdArray = pictureList.getPictures_id().split("_");
        for(String pid : pictureIdArray){
            if(pid.equals("")){
                continue;
            }
            pictureUrlList.add(pictureMapper.getPictureById(new BigInteger(pid)));
        }
        return pictureUrlList;
    }
}
