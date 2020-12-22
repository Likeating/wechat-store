package com.fortwelve.wechatstore.controller;

import com.fortwelve.wechatstore.component.MsgMap;
import com.fortwelve.wechatstore.controller.ValidatedGroup.addManager;
import com.fortwelve.wechatstore.controller.ValidatedGroup.updateManager;
import com.fortwelve.wechatstore.dto.ManagerDTO;
import com.fortwelve.wechatstore.pojo.Manager;
import com.fortwelve.wechatstore.pojo.ManagerRole;
import com.fortwelve.wechatstore.service.ManagerService;
import com.fortwelve.wechatstore.util.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Slf4j
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
        MsgMap msg = new MsgMap();
        try {
            Manager manager = managerService.getManagerByManagerName(username);
            if(manager == null){
                msg.setMeta("管理员不存在。",621);
                return msg;
            }
            if(!password.equals(manager.getManager_password())){
                msg.setMeta("管理员密码不正确。",622);
                return msg;
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


            msg.setMeta("登录成功。",200);
        }catch (Exception e){
//            e.printStackTrace();
            log.info("login出错："+e.getMessage());
            msg.setMeta("服务器出错。",500);
        }
        return msg;
    }
    @PostMapping("/addManager")
    public Object addManager(@Validated(addManager.class) ManagerDTO managerDTO, BindingResult result, HttpServletResponse response){
        MsgMap msg = new MsgMap();

        try {
            //判断字段是否为空
            if (result.hasErrors()){
                msg.setMeta(result.getFieldError().getDefaultMessage(),701);
                return msg;
            }
            if(managerService.getManagerByManagerName(managerDTO.getUsername())!=null){
                msg.setMeta("用户名已经存在。",623);
                return msg;
            }
            //寻找对应角色id
            int id=0;
            boolean find=false;
            List<ManagerRole> managerRoles = managerService.getAllManagerRole();
            for(ManagerRole tmp:managerRoles){
                if(managerDTO.getRole().equals(tmp.getRole_name())){
                    id=tmp.getRole_id();
                    find=true;
                    break;
                }
            }
            if(find == false){
                msg.setMeta("请求不正确：不存在该角色。",701);
                return msg;
            }
            Manager manager=new Manager();
            manager.setManager_name(managerDTO.getUsername());
            manager.setManager_password(managerDTO.getPassword());
            manager.setRealname(managerDTO.getRealname());
            manager.setEmail(managerDTO.getEmail());
            manager.setTel(managerDTO.getTel());
            manager.setSex(managerDTO.getSex());
            manager.setRole_id(id);

            if(managerService.addManager(manager)==0){
                log.info("addManager出错：添加管理员失败。");
                msg.setMeta("服务器出错。",500);
                return msg;
            }

            msg.put("id",manager.getId());
            msg.put("username",manager.getManager_name());
            msg.put("realname",manager.getRealname());
            msg.put("email",manager.getEmail());
            msg.put("tel",manager.getTel());
            msg.put("sex",manager.getSex());
            msg.put("role",managerDTO.getRole());

            msg.setMeta("添加成功。",200);

        }catch (Exception e){
            log.info("addManager出错："+e.getMessage());
            msg.setMeta("服务器出错。",500);
        }
        return msg;
    }
    @PostMapping("/updateManager")
    public Object updateManager(@Validated(updateManager.class) ManagerDTO managerDTO, BindingResult result, HttpServletResponse response){
        MsgMap msg = new MsgMap();

        try{
            if (result.hasErrors()){
                msg.setMeta(result.getFieldError().getDefaultMessage(),701);
                return msg;
            }
            Manager manager=managerService.getManagerByManagerName(managerDTO.getUsername());
            if(manager == null){
                msg.setMeta("管理员不存在。",621);
                return msg;
            }
            //管理员用户名不可以修改
//            manager.setManager_name(managerDTO.getUsername());
            if(managerDTO.getPassword() != null ){
                manager.setManager_password(managerDTO.getPassword());
            }
            if(managerDTO.getRealname() != null ){
                manager.setRealname(managerDTO.getRealname());
            }
            if(managerDTO.getEmail() != null ){
                manager.setEmail(managerDTO.getEmail());
            }
            if(managerDTO.getTel() != null ){
                manager.setTel(managerDTO.getTel());
            }
            if(managerDTO.getSex() != null ){
                manager.setSex(managerDTO.getSex());
            }
            if(managerDTO.getRole() != null ){
                int id=0;
                boolean find=false;
                List<ManagerRole> managerRoles = managerService.getAllManagerRole();

                for(ManagerRole tmp:managerRoles){
                    if(managerDTO.getRole().equals(tmp.getRole_name())){
                        id=tmp.getRole_id();
                        find=true;
                        break;
                    }
                }
                if(find == false){
                    msg.setMeta("请求不正确：不存在该角色。",701);
                    return msg;
                }
                manager.setRole_id(id);
            }

            if(managerService.updateManager(manager)==0){
                msg.setMeta("修改失败。",500);
                return msg;
            }

            msg.put("id",manager.getId());
            msg.put("manager_name",manager.getManager_name());
            msg.put("realname",manager.getRealname());
            msg.put("email",manager.getEmail());
            msg.put("tel",manager.getTel());
            msg.put("sex",manager.getSex());
            msg.put("role",managerService.getManagerRoleById(manager.getRole_id()).getRole_name());

            msg.setMeta("修改成功。",200);
        }catch(Exception e){
            msg.setMeta("服务器出错。",500);
        }
        return msg;
    }
    @PostMapping("/deleteManager")
    public Object deleteManager(int id, HttpServletResponse response){
        MsgMap msg = new MsgMap();
        try{

            if(managerService.deleteManagerById(id)==0){
                msg.setMeta("删除失败。",500);
                return msg;
            }
            msg.put("操作成功",200);

        }catch (Exception e){
            msg.setMeta("服务器出错。",500);
        }
        return msg;
    }
    @GetMapping("/getAllManager")
    public Object getAllManager(){
        MsgMap msg = new MsgMap();
        try{

            List<Manager> managerList=managerService.getAllManager();

            msg.put("total",managerList.size());
            msg.put("list",managerList);

            msg.setMeta("操作成功。",200);
        }catch (Exception e){
            msg.setMeta("服务器出错。",200);
        }
        return msg;
    }
    @GetMapping("/getAllRole")
    public Object getAllRole(){
        MsgMap msg = new MsgMap();
        try{
            List<ManagerRole> managerRoleList=managerService.getAllManagerRole();

            msg.put("total",managerRoleList.size());
            msg.put("list",managerRoleList);

            msg.setMeta("操作成功。",200);
        }catch (Exception e){
            msg.setMeta("服务器出错。",200);
        }
        return msg;
    }
}
