package com.fortwelve.wechatstore.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Map;

public class JWTUtils {

    final static String signature = "qiuminghenshuai";
    public static String getToken(Map<String,String> map){
        Builder builder = JWT.create();
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.MINUTE,30);
        map.forEach((k,v)->{
            builder.withClaim(k,v);
        });
        builder.withExpiresAt(instance.getTime());

        return builder.sign(Algorithm.HMAC256(signature));
    }
    public static Map<String,Claim> verify(String token){
        DecodedJWT decodedJWT=JWT.require(Algorithm.HMAC256(signature)).build().verify(token);
        Map<String, Claim> map =decodedJWT.getClaims();
        return map;
    }
}
