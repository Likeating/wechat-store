package com.fortwelve.wechatstore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

//属性	类型	说明
//        openid	string	用户唯一标识
//        session_key	string	会话密钥
//        unionid	string	用户在开放平台的唯一标识符，在满足 UnionID 下发条件的情况下会返回，详见 UnionID 机制说明。
//        errcode	number	错误码
//        errmsg	string	错误信息
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Code2Session {
    private String openid;
    private String session_key;
    private String unionid;
    private int errcode;
    private String errmsg;
}
