package com.fortwelve.wechatstore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigInteger;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserInfo {

    private BigInteger userId;
    private String nickName;
    private int gender;//性别 1男 2女
    private String language;
    private String city;
    private String province;
    private String country;
    private String avatarUrl;//头像url  https://thirdwx.qlogo.cn/.....
    private Timestamp create_time;//注册时间

}
