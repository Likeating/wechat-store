package com.fortwelve.wechatstore.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class StartThreadApplicationRunner implements ApplicationRunner {
    @Autowired
    OrderCheckerThread orderCheckerThread;
    @Override
    public void run(ApplicationArguments args) throws Exception {
//        System.out.println("ApplicationArguments");
        //程序启动之后，开始订单检查线程
//        orderCheckerThread.start();
    }
}
