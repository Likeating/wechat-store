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

@Component("jwtUtils")
public class JWTUtils {

    @Value("${JWTUtils.signature}")
    private String signature;

    @Value("${JWTUtils.minute}")
    private int minute;


    public String getToken(Map<String,String> map){
        Builder builder = JWT.create();
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.MINUTE,30);
        map.forEach((k,v)->{
            builder.withClaim(k,v);
        });

        builder.withExpiresAt(instance.getTime());
        return builder.sign(Algorithm.HMAC256(signature));
    }
    public Map<String,Claim> decode(String token){
        Map<String, Claim> map =verify(token).getClaims();
        return map;
    }
    public DecodedJWT verify(String token){
        return JWT.require(Algorithm.HMAC256(signature)).build().verify(token);
    }
}
