package com.fortwelve.wechatstore.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Map;
import java.util.Random;

public class JWTUtils {

    public static String getToken(Map<String,String> map,String signature,int minute){
        Builder builder = JWT.create();
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.MINUTE,minute);
        map.forEach((k,v)->{
            builder.withClaim(k,v);
        });

        builder.withExpiresAt(instance.getTime());
        return builder.sign(Algorithm.HMAC256(signature));
    }
    public static Map<String,Claim> decode(String token,String signature){
        Map<String, Claim> map =verify(token,signature).getClaims();
        return map;
    }
    public static DecodedJWT verify(String token,String signature){
        return JWT.require(Algorithm.HMAC256(signature)).build().verify(token);
    }
    public static String getRandomString(int length){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuffer s=new StringBuffer();
        for(int i=0;i<length;i++){
            int number=random.nextInt(62);
            s.append(str.charAt(number));
        }
        return s.toString();
    }
}
