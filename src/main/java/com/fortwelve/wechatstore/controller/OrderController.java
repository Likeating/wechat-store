package com.fortwelve.wechatstore.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("/my/order")
public class OrderController {
    @PostMapping("/create")
    public String create(String order_price, String consignee_addr, String products, HttpServletResponse response){

        return "";
    }
}
