package com.fortwelve.wechatstore;

import com.fortwelve.wechatstore.mapper.CustomerMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@SpringBootTest
class WechatStoreApplicationTests {

    @Autowired
    DataSource dataSource;
    @Autowired
    CustomerMapper customerMapper;
    @Test
    void contextLoads() throws SQLException {
        Connection cn = dataSource.getConnection();
        System.out.println(cn);
        cn.close();
    }

    @Test
    void testMapper(){
        System.out.println(customerMapper.getCustomerById(1l));
    }
}
