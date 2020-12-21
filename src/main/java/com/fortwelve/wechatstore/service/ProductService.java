package com.fortwelve.wechatstore.service;

import com.fortwelve.wechatstore.dto.ProductDetailDTO;
import com.fortwelve.wechatstore.dto.ProductPropertiesDTO;
import com.fortwelve.wechatstore.dto.SkuPropertiesDTO;
import com.fortwelve.wechatstore.pojo.Product;
import com.fortwelve.wechatstore.pojo.Sku;

import java.math.BigInteger;
import java.util.List;

public interface ProductService {

    int addProduct(Product product);
    Product getProductById(BigInteger id);
    List<Product> getAllProduct();
    List<Product> getProductPage(int offset,int rows);
    int deleteProductById(BigInteger id);
    int updateProduct(Product product);

    /**
     * Product转ProductProperties
     * @param product
     * @return ProductPropertiesDTO
     */
    ProductPropertiesDTO getProductProperties(Product product);

    /**
     * Sku转SkuProperties
     * @param sku
     * @return SkuPropertiesDTO
     */
    SkuPropertiesDTO getSkuProperties(Sku sku);
    //    List<Product> searchProductPage(List<String> keywords,int offset,int rows);

    /**
     * 模糊查询，返回ProductProperties列表
     * @param query
     * @param pagenum
     * @param pagesize
     * @return
     */
    List<ProductPropertiesDTO> searchProductPage(List<String> query, int pagenum, int pagesize);

    /**
     * 获取商品详细数据
     * @param product_id
     * @return ProductDetailDTO
     */
    ProductDetailDTO getProductDetail(BigInteger product_id);

    /**
     * 获取图片URL列表
     * @param id
     * @return
     */
    List<String> getPictureUrlList(BigInteger id);
}
