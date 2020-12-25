package com.fortwelve.wechatstore.util;

import com.fortwelve.wechatstore.dto.ProductDTO;
import com.fortwelve.wechatstore.pojo.Product;

public class ProductUtils {
    /**
     * Product转ProductDTO
     * 个别属性没有转，需要手动填写
     * @return
     */
    public static ProductDTO getProductDTOByProduct(Product product){
        ProductDTO productDTO = new ProductDTO();
        productDTO.setProduct_id(product.getProduct_id());
        productDTO.setProduct_name(product.getProduct_name());
        productDTO.setCategory_id(product.getCategory_id());
        productDTO.setPrice(product.getPrice());
        productDTO.setAdd_time(product.getAdd_time());
        productDTO.setDelete_time(product.getDelete_time());
        productDTO.setState(product.getState());
        productDTO.setSale(product.getSale());
        return productDTO;
    }

    public static Product getProductByProductDTO(ProductDTO productDTO){
        Product product = new Product();
        product.setProduct_id(productDTO.getProduct_id());
        product.setProduct_name(productDTO.getProduct_name());
        product.setCategory_id(productDTO.getCategory_id());
        product.setPrice(productDTO.getPrice());
        product.setAdd_time(productDTO.getAdd_time());
        product.setDelete_time(productDTO.getDelete_time());
        product.setState(productDTO.getState());
        product.setSale(productDTO.getSale());
        return product;
    }
}
