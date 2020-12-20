package com.fortwelve.wechatstore.service.Impl;

import com.fortwelve.wechatstore.dao.*;
import com.fortwelve.wechatstore.pojo.Picture;
import com.fortwelve.wechatstore.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
public class PictureServiceImpl implements PictureService {
    @Autowired
    PictureMapper pictureMapper;

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
}
