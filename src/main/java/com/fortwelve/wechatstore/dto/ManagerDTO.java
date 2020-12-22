package com.fortwelve.wechatstore.dto;

import com.fortwelve.wechatstore.controller.ValidatedGroup.addManager;
import com.fortwelve.wechatstore.controller.ValidatedGroup.updateManager;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ManagerDTO {
    private Integer id;
    @Pattern(regexp = "\\S{6,20}",message = "用户名长度必须在6-20之间，且不能包括空白字符。",groups = {addManager.class, updateManager.class})
    @NotBlank(message = "用户名不能为空。",groups = {addManager.class,updateManager.class})
    private String username;
    @Pattern(regexp = "\\S{6,20}",message = "密码长度必须在6-20之间，且不能包括空白字符。",groups = {addManager.class, updateManager.class})
    @NotBlank(message = "密码不能为空。",groups = {addManager.class})
    private String password;
    @Pattern(regexp = ".{1,50}",message = "姓名长度必须在1-50之间。",groups = {addManager.class, updateManager.class})
    @NotBlank(message = "姓名不能为空。",groups = {addManager.class})
    private String realname;
    @Email(message = "邮箱格式不正确。",groups = {addManager.class, updateManager.class})
    @NotBlank(message = "邮箱不能为空。",groups = {addManager.class})
    private String email;
    @Pattern(regexp = ".{1,20}",message = "联系电话长度必须在1-20之间。",groups = {addManager.class, updateManager.class})
    @NotBlank(message = "联系电话不能为空。",groups = {addManager.class})
    private String tel;
    @Pattern(regexp = "[男女]",message = "性别必须是“男”或者“女”。",groups = {addManager.class, updateManager.class})
    @NotBlank(message = "性别不能为空。",groups = {addManager.class})
    private String sex;
    @Pattern(regexp = "\\S{1,20}",message = "请输入正确的角色名。",groups = {addManager.class, updateManager.class})
    @NotBlank(message = "角色不能为空。",groups = {addManager.class})
    private String role;
}
