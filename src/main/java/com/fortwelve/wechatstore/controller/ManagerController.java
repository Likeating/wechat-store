package com.fortwelve.wechatstore.controller;

import com.auth0.jwt.interfaces.Claim;
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
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.DigestUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
//@CrossOrigin
@RestController
@RequestMapping("/manager")
public class ManagerController {

    @Value("${JWTUtils.manager.signature}")
    private String managerSignature;
    @Value("${JWTUtils.manager.minute}")
    private int managerMinute;
    @Value("${Manager.codeTTL}")
    private long codeTTL;

    @Autowired
    ManagerService managerService;
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @GetMapping("/verify")
    public Object verify(@RequestParam String username){
        MsgMap msg = new MsgMap();
        try{
            String randomCode = JWTUtils.getRandomString(4);
            //放入redis缓存
            ValueOperations<String,String> valueOperations = stringRedisTemplate.opsForValue();
            valueOperations.set("verify_code_"+username,randomCode,codeTTL, TimeUnit.SECONDS);

            msg.put("username",username);
            msg.put("code",randomCode);
            msg.setMeta("操作成功。",200);
        }catch (Exception e){
            e.printStackTrace();
            log.info(e.getMessage());
            msg.setMeta("服务器出错。",500);
        }
        return msg;
    }

    @PostMapping("/login")
    public Object login(@RequestParam String username, @RequestParam String data, HttpServletResponse response){
        MsgMap msg = new MsgMap();
        try {
            Manager manager = managerService.getManagerByManagerName(username);
            if(manager == null){
                msg.setMeta("管理员不存在。",621);
                return msg;
            }
            //从redis缓存中取出code并移除缓存
            ValueOperations<String,String> valueOperations = stringRedisTemplate.opsForValue();
            String code = valueOperations.get("verify_code_"+username);
            valueOperations.getOperations().delete("verify_code_"+username);
            if(null == code){
                msg.setMeta("用户未验证或code过期。",624);
                return msg;
            }
            String beforeMD5=manager.getManager_name()+manager.getManager_password()+code;
            String md5Str = DigestUtils.md5DigestAsHex(beforeMD5.getBytes("utf-8")).toUpperCase();

            if(!md5Str.equals(data.toUpperCase())){
                msg.setMeta("管理员密码不正确。",622);
                return msg;
            }
            ManagerRole managerRole= managerService.getManagerRoleById(manager.getRole_id());

            //生成token
            Map<String,String> tokenMap = new HashMap<>();
            tokenMap.put("id",String.valueOf(manager.getId()));
            tokenMap.put("username",String.valueOf(manager.getManager_name()));
            tokenMap.put("role",managerRole.getRole_name());
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
    public Object addManager(@Validated(addManager.class) ManagerDTO managerDTO, BindingResult result, HttpServletRequest request){
        MsgMap msg = new MsgMap();

        try {
            //判断字段是否为空
            if (result.hasErrors()){
                msg.setMeta(result.getFieldError().getDefaultMessage(),701);
                return msg;
            }
            String token = request.getHeader("token");
            Map<String, Claim> tokenMap = JWTUtils.decode(token,managerSignature);
            String role = tokenMap.get("role").asString();
            if(!role.equals("超级管理员")){
                msg.setMeta("没有权限操作。",611);
                return msg;
            }
            Manager isExist=managerService.getManagerByManagerName(managerDTO.getUsername());
            if(isExist != null){
                msg.setMeta("管理员已经存在。",623);
                return msg;
            }

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
    public Object updateManager(@Validated(updateManager.class) ManagerDTO managerDTO, BindingResult result, HttpServletRequest request){
        MsgMap msg = new MsgMap();

        try{
            String token = request.getHeader("token");
            Map<String,Claim> tokenMap = JWTUtils.decode(token,managerSignature);
            String idstr = tokenMap.get("id").asString();
            String userName = tokenMap.get("username").asString();
            String role = tokenMap.get("role").asString();
            if (result.hasErrors()){
                msg.setMeta(result.getFieldError().getDefaultMessage(),701);
                return msg;
            }
            Manager manager=managerService.getManagerByManagerName(managerDTO.getUsername());
            if(manager == null){
                msg.setMeta("管理员不存在。",621);
                return msg;
            }
            if(!role.equals("超级管理员")){
                //如果不是超级管理员，就判断修改的管理员信息是否是本人
                if(!userName.equals(manager.getManager_name())){
                    //既不是超级管理员，又不是修改自己信息，就不允许
                    msg.setMeta("没有权限修改他人信息。",611);
                    return msg;
                }
            }
            //是超级管理员，或者不是超级管理员但修改的是自己信息，才能走到这里

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
                ManagerRole managerRole=null;
                List<ManagerRole> managerRoles = managerService.getAllManagerRole();

                for(ManagerRole tmp:managerRoles){
                    if(managerDTO.getRole().equals(tmp.getRole_name())){
                        managerRole=tmp;
                        break;
                    }
                }

                if(managerRole == null){
                    msg.setMeta("请求不正确：不存在该角色。",701);
                    return msg;
                }
                if(managerRole.getRole_id()!=manager.getRole_id()){
                    //角色不一致，修改角色
                    //超级管理员可以修改任意人信息，但不能修改自己角色
                    //其他管理员不可以修改角色
                    if(role.equals("超级管理员")){
                        if(userName.equals(manager.getManager_name())){
                            msg.setMeta("超级管理员不能修改自己角色。",611);
                            return msg;
                        }
                    }else {
                        msg.setMeta("没有权限修改角色。",611);
                        return msg;
                    }
                    manager.setRole_id(managerRole.getRole_id());
                }
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
    public Object deleteManager(@RequestParam int id, HttpServletRequest request){
        MsgMap msg = new MsgMap();
        try{
            //权限判断
            String token = request.getHeader("token");
            Map<String,Claim> tokenMap = JWTUtils.decode(token,managerSignature);
            String role = tokenMap.get("role").asString();
            if(!role.equals("超级管理员")){
                msg.setMeta("没有权限删除管理员。",611);
                return msg;
            }
            Manager manager = managerService.getManagerById(id);
            if(manager == null){
                msg.setMeta("管理员不存在。",621);
                return msg;
            }
            ManagerRole managerRole = managerService.getManagerRoleById(manager.getId());
            if(managerRole.getRole_name().equals("超级管理员")){
                msg.setMeta("没有权限删除超级管理员。",611);
                return msg;
            }
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
    @RequestMapping("/getManagers")
    public Object getManagers(Integer currentPage,Integer pageSize){
        MsgMap msg = new MsgMap();
        try{
            List<Manager> managerList;
            if(pageSize==null){
                managerList=managerService.getAllManager();
            }else {
                int current;
                if(currentPage==null || currentPage<0){
                    current=1;
                }else{
                    current=currentPage;
                }
                int head=(pageSize*(current-1));
                managerList=managerService.getManagerPage(head,pageSize);
                msg.put("currentPage",current);

            }
            for (Manager tmp : managerList){
                tmp.setManager_password(null);
            }
            msg.put("total",managerList.size());
            msg.put("list",managerList);

            msg.setMeta("操作成功。",200);
        }catch (Exception e){
            log.info("getManagers出错："+e.getMessage());
            msg.setMeta("服务器出错。",200);
        }
        return msg;
    }
    @RequestMapping("/getAllRole")
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
