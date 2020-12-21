package com.fortwelve.wechatstore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ManagerDTO {
    private Integer id;
    private String username;
    private String password;
    private String realname;
    private String email;
    private String tel;
    private String sex;
    private String role;
}
