package com.fortwelve.wechatstore.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fortwelve.wechatstore.dto.Code2SessionDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component("wxapi")
public class WXapi {
    @Value("${WXApi.appid}")
    private String appid;
    @Value("${WXApi.secret}")
    private String secret;

    /**
     * 获取openid
     * @param js_code
     * @return Code2SessionDTO(openid=oUhCD4sItcSXf24odsTHY9tllcNQ, session_key=a9xluJbwVSx6EjuxZEbTqw==, unionid=null, errcode=0, errmsg=null)
     * @throws IOException
     */
    public Code2SessionDTO Code2Session(String js_code)throws IOException{
        URL url = new URL("https://api.weixin.qq.com/sns/jscode2session?appid="+appid+"&secret="+secret+"&js_code="+js_code+"&grant_type=authorization_code");
        ObjectMapper objectMapper = new ObjectMapper();
        Code2SessionDTO code2SessionDTO = objectMapper.readValue(url, Code2SessionDTO.class);
        return code2SessionDTO;
    }

    /**
     * 私有函数，格式化
     * @param bytes
     * @return
     */
    private String getFormattedText(byte[] bytes) {
        final char[] HEX = {'0', '1', '2', '3', '4', '5',
                '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        int len = bytes.length;
        StringBuilder buf = new StringBuilder(len * 2);
        // 把密文转换成十六进制的字符串形式
        for (int j = 0; j < len; j++) {
            buf.append(HEX[(bytes[j] >> 4) & 0x0f]);
            buf.append(HEX[bytes[j] & 0x0f]);
        }
        return buf.toString();
    }

    /**
     * SHA1，数据校验
     * @param data
     * @return SHA1加密值
     * @throws NoSuchAlgorithmException
     */

    public String getSHA1(String data) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
        messageDigest.update(data.getBytes());
        return getFormattedText(messageDigest.digest());
    }
}
