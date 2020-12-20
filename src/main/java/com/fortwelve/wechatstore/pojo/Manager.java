package com.fortwelve.wechatstore.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Manager {
    private int id;
    private String manager_name;
    private String manager_password;
    private String realname;
    private String email;
    private String tel;
    private String sex;
    private int role_id;
}
