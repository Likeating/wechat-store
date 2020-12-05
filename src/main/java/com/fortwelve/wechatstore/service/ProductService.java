package com.fortwelve.wechatstore.service;

import com.fortwelve.wechatstore.pojo.Product;

import java.math.BigInteger;
import java.util.List;

public interface ProductService {
    int addProduct(Product product);
    Product getProductById(BigInteger id);
    List<Product> getAllProduct();
    List<Product> getProductPage(int offset,int rows);
    int deleteProductById(BigInteger id);
    int updateProduct(Product product);
    List<Product> searchProductPage(List<String> keywords,int offset,int rows);
}
