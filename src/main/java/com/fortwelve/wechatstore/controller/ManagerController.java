package com.fortwelve.wechatstore.controller;

import com.fortwelve.wechatstore.dto.ManagerDTO;
import com.fortwelve.wechatstore.pojo.Manager;
import com.fortwelve.wechatstore.pojo.ManagerRole;
import com.fortwelve.wechatstore.service.ManagerService;
import com.fortwelve.wechatstore.util.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/manager")
public class ManagerController {

    @Autowired
    ManagerService managerService;

    @Value("${JWTUtils.manager.signature}")
    private String managerSignature;
    @Value("${JWTUtils.manager.minute}")
    private int managerMinute;

    @PostMapping("/login")
    public Object login(@RequestParam String username, @RequestParam String password, HttpServletResponse response){
        Map<String,Object> map = new HashMap<>();
        Map<String,Object> meta = new HashMap<>();
        Map<String,Object> msg = new HashMap<>();
        try {
            Manager manager = managerService.getManagerByManagerName(username);
            if(manager == null){
                meta.put("msg","管理员不存在。");
                meta.put("status",621);
                map.put("meta",meta);
                return map;
            }
            if(!password.equals(manager.getManager_password())){
                meta.put("msg","管理员密码不正确。");
                meta.put("status",622);
                map.put("meta",meta);
                return map;
            }
            ManagerRole managerRole= managerService.getManagerRoleById(manager.getRole_id());

            //生成token
            Map<String,String> tokenMap = new HashMap<>();
            tokenMap.put("id",String.valueOf(manager.getId()));
            tokenMap.put("username",String.valueOf(manager.getManager_name()));
            tokenMap.put("roleid",String.valueOf(managerRole.getRole_id()));
            String token = JWTUtils.getToken(tokenMap,managerSignature,managerMinute);
            //设置token
            response.setHeader("token",token);
            //返回管理员信息
            msg.put("id",manager.getId());
            msg.put("username",manager.getManager_name());
            msg.put("realname",manager.getRealname());
            msg.put("email",manager.getEmail());
            msg.put("tel",manager.getTel());
            msg.put("sex",manager.getSex());
            msg.put("role",managerRole.getRole_name());


            meta.put("msg","登录成功。");
            meta.put("status",200);

            meta.put("message",msg);
            map.put("meta",meta);
        }catch (Exception e){
            meta.put("msg","服务器出错。");
            meta.put("status",500);
            map.put("meta",meta);
        }
        return map;
    }
    @PostMapping("/addManager")
    public Object addManager(ManagerDTO managerDTO, HttpServletResponse response){
        Map<String,Object> map = new HashMap<>();
        Map<String,Object> meta = new HashMap<>();
        Map<String,Object> msg = new HashMap<>();

        try {
            //判断字段是否为空
            if(managerDTO.getUsername()==null ||
                    managerDTO.getPassword()==null ||
                    managerDTO.getRealname()==null ||
                    managerDTO.getEmail()==null ||
                    managerDTO.getTel()==null ||
                    managerDTO.getSex()==null ||
                    managerDTO.getRole()==null){
            }



        }catch (Exception e){
            meta.put("msg","服务器出错。");
            meta.put("status",500);
            map.put("meta",meta);
        }
        return map;
    }
}
