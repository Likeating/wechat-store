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
    private String realname;
    private String manager_password;
    private String email;
    private String sex;
    private String tel;
    private int role_id;
}
