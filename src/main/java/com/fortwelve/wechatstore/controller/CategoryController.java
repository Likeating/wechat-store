package com.fortwelve.wechatstore.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/home")
public class CategoryController {
    @RequestMapping("/categories")
    //@RequestMapping(value = "/categories",produces = "application/json;charset=utf-8")
    public Object categories(){
        Map<String,Object> map = new HashMap<>();
        Map<String,Object> meta = new HashMap<>();
        List<Object> msg = new LinkedList();
        //Map<String,Object> tmp = new HashMap<>();
        Map<String,Object> category = new HashMap<>();
        List<Object> children = new LinkedList();
        List<Object> cate_list = new LinkedList();
        List<Object> cate_list11=new LinkedList();
        Map<String,Object> list =new HashMap<>();
        Map<String,Object> cate1 = new HashMap<>();
        Map<String,Object> cate2 = new HashMap<>();
        Map<String,Object> cate3 = new HashMap<>();
        Map<String,Object> cate4 = new HashMap<>();

        category.put("cat_id",1);
        category.put("cat_name","男装");
        category.put("cat_pid",0);
        category.put("cat_icon","");

        list.put("cat","男");

        cate1.put("cat_id",5);
        cate1.put("cat_name","衬衫");
        cate1.put("cat_pid",3);
        cate1.put("cat_icon","https://s3.ax1x.com/2020/12/16/r1UVgO.jpg");

        cate2.put("cat_id",6);
        cate2.put("cat_name","牛仔裤");
        cate2.put("cat_icon","https://s3.ax1x.com/2020/12/16/r1UAC6.jpg");

        cate3.put("cat_id",7);
        cate3.put("cat_name","外套");
        cate3.put("cat_icon","https://s3.ax1x.com/2020/12/16/r1UE8K.jpg");

        cate4.put("cat_id",8);
        cate4.put("cat_name","休闲裤");
        cate4.put("cat_icon","https://s3.ax1x.com/2020/12/16/r1UF4x.jpg");
        cate_list.add(cate1);
        cate_list.add(cate2);
        cate_list.add(cate3);
        cate_list.add(cate4);

        //category.put("children",cate_list);
        list.put("children",cate_list);
        cate_list11.add(list);
        category.put("children",cate_list11);

        msg.add(category);

        Map<String,Object> category2 = new HashMap<>();
        List<Object> cate_list2 = new LinkedList();
        List<Object> cate_list21=new LinkedList();
        Map<String,Object> list2 =new HashMap<>();
        Map<String,Object> cate21 = new HashMap<>();
        Map<String,Object> cate22 = new HashMap<>();
        Map<String,Object> cate23 = new HashMap<>();
        Map<String,Object> cate24 = new HashMap<>();

        category2.put("cat_id",2);
        category2.put("cat_name","女装");
        category2.put("cat_pid",0);
        category2.put("cat_icon","");

        list2.put("cat","女");

        cate21.put("cat_id",9);
        cate21.put("cat_name","T恤");
        cate21.put("cat_icon","https://s3.ax1x.com/2020/12/16/r1UiU1.jpg");

        cate22.put("cat_id",10);
        cate22.put("cat_name","牛仔裤");
        cate22.put("cat_icon","https://s3.ax1x.com/2020/12/16/r1UZvD.jpg");

        cate23.put("cat_id",11);
        cate23.put("cat_name","长袖");
        cate23.put("cat_icon","https://s3.ax1x.com/2020/12/16/r1UnDH.jpg");

        cate24.put("cat_id",12);
        cate24.put("cat_name","羽绒");
        cate24.put("cat_icon","https://s3.ax1x.com/2020/12/16/r1UmKe.jpg");
        cate_list2.add(cate21);
        cate_list2.add(cate22);
        cate_list2.add(cate23);
        cate_list2.add(cate24);

        //category2.put("children",cate_list2);
        list2.put("children",cate_list2);
        cate_list21.add(list2);
        category2.put("children",cate_list21);
        msg.add(category2);

        Map<String,Object> category3 = new HashMap<>();
        List<Object> cate_list31=new LinkedList();
        Map<String,Object> list3 =new HashMap<>();
        List<Object> cate_list3 = new LinkedList();
        Map<String,Object> cate31 = new HashMap<>();
        Map<String,Object> cate32 = new HashMap<>();

        category3.put("cat_id",3);
        category3.put("cat_name","当月新品");
        category3.put("cat_pid",0);
        category3.put("cat_icon","");

        list3.put("cat","当月新品");

        cate31.put("cat_id",13);
        cate31.put("cat_name","联名新品");

        cate32.put("cat_id",14);
        cate32.put("cat_name","当季新品");

        cate_list3.add(cate31);
        cate_list3.add(cate32);

        //category3.put("children",cate_list3);
        list3.put("children",cate_list3);
        cate_list31.add(list3);
        category3.put("children",cate_list31);
        msg.add(category3);

        Map<String,Object> category4 = new HashMap<>();
        List<Object> cate_list41=new LinkedList();
        Map<String,Object> list4 =new HashMap<>();
        List<Object> cate_list4 = new LinkedList();
        Map<String,Object> cate41 = new HashMap<>();
        Map<String,Object> cate42 = new HashMap<>();
        Map<String,Object> cate43 = new HashMap<>();

        category4.put("cat_id",4);
        category4.put("cat_name","当月热销");
        category4.put("cat_pid",0);
        category4.put("cat_icon","");

        list4.put("cat","当月热销");

        cate41.put("cat_id",15);
        cate41.put("cat_name","上衣");

        cate42.put("cat_id",16);
        cate42.put("cat_name","裤子");

        cate43.put("cat_id",17);
        cate43.put("cat_name","外套");

        cate_list4.add(cate41);
        cate_list4.add(cate42);
        cate_list4.add(cate43);
        //category4.put("children",cate_list4);
        list4.put("children",cate_list4);
        cate_list41.add(list4);
        category4.put("children",cate_list41);
        msg.add(category4);

        meta.put("msg","获取成功");
        meta.put("status",200);

        map.put("message",msg);
        map.put("meta",meta);

        return map;
    }
}