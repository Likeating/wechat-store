package com.fortwelve.wechatstore;

import com.fortwelve.wechatstore.mapper.CategoryMapper;
import com.fortwelve.wechatstore.mapper.CustomerMapper;
import com.fortwelve.wechatstore.mapper.PictureMapper;
import com.fortwelve.wechatstore.pojo.Category;
import com.fortwelve.wechatstore.pojo.Customer;
import com.fortwelve.wechatstore.pojo.Picture;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.SQLException;

@SpringBootTest
class WechatStoreApplicationTests {

    @Autowired
    DataSource dataSource;
    @Autowired
    CustomerMapper customerMapper;
    @Autowired
    CategoryMapper categoryMapper;
    @Autowired
    PictureMapper pictureMapper;


    @Test
    void contextLoads() throws SQLException {
        Connection cn = dataSource.getConnection();
        System.out.println(cn);
        cn.close();
    }

    @Test
    void testMapper(){
        System.out.println(customerMapper.getCustomerById(new java.math.BigInteger("1")));
        System.out.println(customerMapper.getAllCustomer());

        System.out.println(categoryMapper.getCategoryById(2));

        Category category = new Category();
        category.setCategory_name("分类名");
//        System.out.println(categoryMapper.addCategory(category));
//        System.out.println(categoryMapper.getAllCategory());
//        Category category2 = new Category();
//        category2.setCategory_id(8);
//        category2.setCategory_name("修改后的分类名");
//        System.out.println(categoryMapper.updateCategory(category2));
//        System.out.println(categoryMapper.deleteCategory(7));
    }

    @Test
    void testCustomerMapper(){
        System.out.println("添加志杰");
        Customer customer1 = new Customer();
        customer1.setCustomer_name("陈志杰");
        customerMapper.addCustomer(customer1);
        System.out.println(customer1.getCustomer_id());
//        System.out.println("获取梓鹏");
//
//        Customer customer2=customerMapper.getCustomerById(new java.math.BigInteger("1"));
//        System.out.println(customer2);
//        customer2.setCustomer_name("邓梓鹏");
//
//        System.out.println("修改梓鹏为邓梓鹏");
//
//        System.out.println(customerMapper.updateCustomer(customer2));
//
//        System.out.println("添加错误名字");
//        Customer customer3 = new Customer(new java.math.BigInteger("3"),"dingyunyu");
//        customerMapper.addCustomer(customer3);
//        System.out.println(customerMapper.getAllCustomer());
//        System.out.println("插入后的主键");
//        System.out.println(customer3.getCustomer_id());
//        System.out.println("删除错误名字");
//        System.out.println(customerMapper.deleteCustomerById(new BigInteger("3")));

    }

    @Test
    void testPictureMapper(){
        Picture picture1 = new Picture();
        picture1.setUrl("picture1.jpg");
        pictureMapper.addPicture(picture1);

        System.out.println("返回的picture_id："+picture1.getPicture_id());
        Picture picture2 = new Picture(new java.math.BigInteger("1"),"url3.jpg");
        pictureMapper.addPicture(picture2);
        System.out.println("返回的picture_id："+picture2.getPicture_id());
        System.out.println(pictureMapper.getAllPicture());
        System.out.println(pictureMapper.getPictureById(picture2.getPicture_id()));
        picture2.setUrl("url2.jpg");
        pictureMapper.updatePicture(picture2);
        System.out.println(pictureMapper.deletePictureById(picture1.getPicture_id()));
        System.out.println(pictureMapper.getAllPicture());
    }
}
