package com.fortwelve.wechatstore.service;

import com.fortwelve.wechatstore.mapper.ProductMapper;
import com.fortwelve.wechatstore.pojo.Product;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;
import java.util.List;

public class ProductService {
    @Autowired
    ProductMapper productMapper;

    public int addProduct(Product product){
        return productMapper.addProduct(product);
    }

    public Product getProductById(BigInteger id){
        return productMapper.getProductById(id);
    }


    public List<Product> getAllProduct(){
        return productMapper.getAllProduct();
    }


    public List<Product> getProductPage(int offset,int rows){
        return productMapper.getProductPage(offset,rows);
    }


    public int deleteProductById(BigInteger id){
        return productMapper.deleteProductById(id);
    }

    public int updateProduct(Product product){
        return productMapper.updateProduct(product);
    }
}
