package com.fortwelve.wechatstore.controller;

import com.auth0.jwt.interfaces.Claim;
import com.fortwelve.wechatstore.component.MsgMap;
import com.fortwelve.wechatstore.controller.ValidatedGroup.addManager;
import com.fortwelve.wechatstore.controller.ValidatedGroup.updateManager;
import com.fortwelve.wechatstore.dto.ManagerDTO;
import com.fortwelve.wechatstore.pojo.Customer;
import com.fortwelve.wechatstore.pojo.Manager;
import com.fortwelve.wechatstore.pojo.ManagerRole;
import com.fortwelve.wechatstore.service.CustomerService;
import com.fortwelve.wechatstore.service.ManagerService;
import com.fortwelve.wechatstore.util.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.DigestUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
//@CrossOrigin
@RestController
@RequestMapping("/customer")
public class CustomerManageController {

    @Value("${JWTUtils.manager.signature}")
    private String managerSignature;
    @Value("${JWTUtils.manager.minute}")
    private int managerMinute;

    @Autowired
    CustomerService customerService;

    @GetMapping("/getCustomers")
    public Object getCustomers(String keys, BigInteger customer_id, Integer currentPage, Integer pageSize){
        MsgMap msg = new MsgMap();
        try{
            String str;
            List<String> keywords=null;
            if(keys!=null && ! (str = keys.trim()).equals("")){//有关键字
                System.out.println(str);
                keywords = Arrays.asList(str.split("\\s+"));
            }
            Integer start = null;
            if(null != currentPage){
                if(currentPage < 1){
                    currentPage = 1;
                }
                start = (currentPage - 1)*pageSize;
            }else {
                currentPage = 1;
            }
            List<Customer> customerList = customerService.searchCustomer(keywords,customer_id,start,pageSize);
            int total = customerService.searchCustomer(keywords,customer_id,null,null).size();

            msg.put("currentPage",currentPage);
            msg.put("total",total);
            msg.put("customers",customerList);


            msg.setMeta("查询成功。",200);
        }catch (Exception e){
            e.printStackTrace();
            log.debug("/customer/getCustomers出错："+e.getMessage());
            msg.setMeta("服务器出错。",500);
        }
        return msg;
    }



}
