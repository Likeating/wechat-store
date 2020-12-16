package com.fortwelve.wechatstore.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class CategoryController {

    @RequestMapping(value = "/categories",produces = "application/json;charset=utf-8")
    public String categories(){

        return "{\n" +
                "  \"message\": [\n" +
                "    {\n" +
                "      \"cat_id\": 1,\n" +
                "      \"cat_name\": \"男装\",\n" +
                "      \"cat_pid\": 0,\n" +
                "      \"cat_level\": 0,\n" +
                "      \"cat_deleted\": false,\n" +
                "      \"cat_icon\": \"\",\n" +
                "      \"children\": [\n" +
                "        {\n" +
                "          \"cat_id\": 3,\n" +
                "          \"cat_name\": \"电视\",\n" +
                "          \"cat_pid\": 1,\n" +
                "          \"cat_level\": 1,\n" +
                "          \"cat_deleted\": false,\n" +
                "          \"cat_icon\": \"\",\n" +
                "          \"children\": [\n" +
                "            {\n" +
                "              \"cat_id\": 5,\n" +
                "              \"cat_name\": \"曲面电视\",\n" +
                "              \"cat_pid\": 3,\n" +
                "              \"cat_level\": 2,\n" +
                "              \"cat_deleted\": false,\n" +
                "              \"cat_icon\": \"https://api-hmugo-web.itheima.net/full/2fb113b32f7a2b161f5ee4096c319afedc3fd5a1.jpg\"\n" +
                "            }\n" +
                "          ]\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  ],\n" +
                "  \"meta\": {\n" +
                "    \"msg\": \"获取成功\",\n" +
                "    \"status\": 200\n" +
                "  }\n" +
                "}";
    }
}
