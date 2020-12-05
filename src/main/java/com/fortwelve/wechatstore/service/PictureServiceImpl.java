package com.fortwelve.wechatstore.service;

import com.fortwelve.wechatstore.dao.PictureMapper;
import com.fortwelve.wechatstore.pojo.Picture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
public class PictureServiceImpl implements PictureService{
    @Autowired
    PictureMapper pictureMapper;

    public int addPicture(Picture picture){
        return pictureMapper.addPicture(picture);
    }
    public int deletePictureById(BigInteger id){
        return pictureMapper.deletePictureById(id);
    }
    public Picture getPictureById(BigInteger id){
        return pictureMapper.getPictureById(id);
    }
    public List<Picture> getAllPicture(){
        return pictureMapper.getAllPicture();
    }
    public int updatePicture(Picture picture){
        return pictureMapper.updatePicture(picture);
    }

}
