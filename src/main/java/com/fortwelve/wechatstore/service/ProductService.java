package com.fortwelve.wechatstore.service;

import com.fortwelve.wechatstore.dto.ProductDetail;
import com.fortwelve.wechatstore.dto.ProductProperties;
import com.fortwelve.wechatstore.dto.SkuProperties;
import com.fortwelve.wechatstore.pojo.Picture;
import com.fortwelve.wechatstore.pojo.Product;
import com.fortwelve.wechatstore.pojo.Sku;

import java.math.BigInteger;
import java.util.List;

public interface ProductService {

    ProductProperties getProductProperties(Product product);
    SkuProperties getSkuProperties(Sku sku);
    List<ProductProperties> searchProductPage(List<String> query,int pagenum,int pagesize);
    ProductDetail getProductDetail(BigInteger product_id);
    List<String> getPictureUrlList(BigInteger id);
}
