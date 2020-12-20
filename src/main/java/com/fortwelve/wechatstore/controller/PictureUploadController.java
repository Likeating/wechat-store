package com.fortwelve.wechatstore.controller;

import com.fortwelve.wechatstore.pojo.Picture;
import com.fortwelve.wechatstore.service.PictureService;
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

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostMapping("/upload")
    public Object upload(HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        Map<String,Object> meta = new HashMap<>();
        Map<String,Object> msg = new HashMap<>();

        List<MultipartFile> files = ((MultipartHttpServletRequest)request).getFiles("picture");
        List<Picture> pictures = new LinkedList<>();

        try {

            if (files.size() ==0) {
                meta.put("msg","请求不正确，没有上传文件。");
                meta.put("status",701);
                map.put("meta",meta);
                return map;
            }
            for (MultipartFile multipartFile : files){
                if(multipartFile.isEmpty()){
                    meta.put("msg","请求不正确，存在空文件。");
                    meta.put("status",701);
                    map.put("meta",meta);
                    return map;
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

            meta.put("msg","上传成功。");
            meta.put("status",200);

            map.put("meta",meta);
            map.put("msg",msg);
        }catch (Exception e){
            logger.error(e.getMessage());
            e.printStackTrace();
            meta.put("msg","服务器出错。");
            meta.put("status",500);
            map.put("meta",meta);
        }
        return map;
    }


}
