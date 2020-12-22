package com.fortwelve.wechatstore.controller;

import com.fortwelve.wechatstore.component.MsgMap;
import com.fortwelve.wechatstore.pojo.Picture;
import com.fortwelve.wechatstore.service.PictureService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.*;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/pictureUpload")
public class PictureUploadController {

    @Value("${Picture.path}")
    String picturePath;
    @Value("${Picture.pattern}")
    String picturePattern;

    @Autowired
    PictureService pictureService;

    @PostMapping("/upload")
    public Object upload(HttpServletRequest request){
        MsgMap msg = new MsgMap();

        List<MultipartFile> files = ((MultipartHttpServletRequest)request).getFiles("picture");
        List<Picture> pictures = new LinkedList<>();

        try {

            if (files.size() ==0) {
                msg.setMeta("请求不正确，没有上传文件。",701);
                return msg;
            }
            for (MultipartFile multipartFile : files){
                if(multipartFile.isEmpty()){
                    msg.setMeta("请求不正确，存在空文件。",701);
                    return msg;
                }
            }
            for (MultipartFile multipartFile : files){
//                logger.info("是否为空："+multipartFile.isEmpty());
                String oldName = multipartFile.getOriginalFilename();
                String suffix = oldName.substring(oldName.lastIndexOf("."));

                File localFile = new File(picturePath+ UUID.randomUUID()+suffix);
                //据说uuid基本不可能重复，但还是做个判断
                if(localFile.exists()){
                    localFile = new File(picturePath+ UUID.randomUUID()+ Calendar.getInstance().getTimeInMillis() +suffix);
                }
                FileOutputStream fileOutputStream = new FileOutputStream(localFile);
                fileOutputStream.write(multipartFile.getBytes());
                fileOutputStream.close();

                Picture picture = new Picture();
                picture.setUrl(picturePattern.substring(0,picturePattern.lastIndexOf("/")+1)+localFile.getName());
                pictureService.addPicture(picture);

                pictures.add(picture);
            }
            msg.put("pictures",pictures);
            msg.setMeta("上传成功。",200);
        }catch (Exception e){
            log.error(e.getMessage());
            e.printStackTrace();
            msg.setMeta("服务器出错。",500);
        }
        return msg;
    }


}
