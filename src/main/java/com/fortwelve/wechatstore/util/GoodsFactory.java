package com.fortwelve.wechatstore.util;

import com.fortwelve.wechatstore.dto.ProductProperties;
import com.fortwelve.wechatstore.dto.SkuProperties;
import com.fortwelve.wechatstore.pojo.Picture;
import com.fortwelve.wechatstore.pojo.Product;
import com.fortwelve.wechatstore.pojo.Sku;
import org.springframework.stereotype.Component;

@Component
public class GoodsFactory {

    public ProductProperties getProductProperties(Product product, Picture picture, int Stock){
        return new ProductProperties(product.getProduct_id(),
                product.getProduct_name(),
                product.getPrice(),
                product.getCategory_id(),
                Stock,
                product.getAdd_time(),
                product.getDelete_time(),
                picture.getUrl());
    }
    public SkuProperties getSkuProperties(Sku sku,Picture picture){
        return new SkuProperties(sku.getSku_id(),
                sku.getProduct_id(),
                sku.getProperties(),
                sku.getSku_price(),
                sku.getStock(),
                picture.getUrl());
    }

}
