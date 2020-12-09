package com.fortwelve.wechatstore.service.Impl;

import com.fortwelve.wechatstore.dao.*;
import com.fortwelve.wechatstore.dto.ProductDetail;
import com.fortwelve.wechatstore.dto.ProductProperties;
import com.fortwelve.wechatstore.dto.SkuProperties;
import com.fortwelve.wechatstore.pojo.Picture;
import com.fortwelve.wechatstore.pojo.PictureList;
import com.fortwelve.wechatstore.pojo.Product;
import com.fortwelve.wechatstore.pojo.Sku;
import com.fortwelve.wechatstore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductMapper productMapper;
    @Autowired
    SkuMapper skuMapper;
    @Autowired
    PictureListMapper pictureListMapper;
    @Autowired
    PictureMapper pictureMapper;
    @Autowired
    PropertyKeyMapper propertyKeyMapper;
    @Autowired
    PropertyValueMapper propertyValueMapper;

    @Override
    public ProductProperties getProductProperties(Product product) {
        return new ProductProperties(product.getProduct_id(),
                product.getProduct_name(),
                product.getPrice(),
                product.getCategory_id(),
                skuMapper.getStockByProductId(product.getProduct_id()),
                product.getAdd_time(),
                product.getDelete_time(),
                pictureMapper.getPictureById(product.getPicture_id()).getUrl());
    }

    @Override
    public SkuProperties getSkuProperties(Sku sku) {
        return new SkuProperties(sku.getSku_id(),
                sku.getProduct_id(),
                sku.getProperties(),
                sku.getSku_price(),
                sku.getStock(),
                pictureMapper.getPictureById(sku.getPicture_id()).getUrl());
    }

    @Override
    public List<ProductProperties> searchProductPage(List<String> query,int pagenum,int pagesize) {
        List<Product> products = productMapper.searchProductPage(query,pagenum,pagesize);
        List<ProductProperties> productPropertiesList = new LinkedList<>();

        for(Product product:products){
            productPropertiesList.add(getProductProperties(product));
        }
        return productPropertiesList;
    }
    @Override
    public List<String> getPictureUrlList(BigInteger id) {
        //获取图集
        PictureList pictureList = pictureListMapper.getPictureListById(id);
        List<String> pictureUrlList = new LinkedList<>();
        String [] pictureIdArray = pictureList.getPictures_id().split("_");
        if (pictureIdArray.length>0){
            for(String pid : pictureIdArray){
                pictureUrlList.add(pictureMapper.getPictureById(new BigInteger(pid)).getUrl());
            }
        }
        return pictureUrlList;
    }
    @Override
    public ProductDetail getProductDetail(BigInteger product_id) {
        ProductDetail productDetail = new ProductDetail();
        Product product = productMapper.getProductById(product_id);
        //商品不存在
        if (product==null){

        }
        //商品下架
        if(product.getDelete_time()!=null){

        }

        //获取sku属性、key、value
        List<Sku> skuList = skuMapper.getSkuByProductId(product.getProduct_id());
        List<SkuProperties> skuPropertiesList = new LinkedList<>();

        String [] properties;
        String [] kv;
        Map<String,String> keys = new HashMap<>();
        Map<String,String> values = new HashMap<>();
        for (Sku sku : skuList){
            properties=sku.getProperties().split("_");
            for(String property:properties){
                kv = property.split(":");
                if(keys.get(kv[0])==null){
                    keys.put(kv[0],propertyKeyMapper.getPropertyKeyById(new BigInteger(kv[0])).getKey_name());
                }
                if(values.get(kv[1])==null){
                    values.put(kv[1],propertyValueMapper.getPropertyValueById(new BigInteger(kv[1])).getValue_name());
                }
            }
            skuPropertiesList.add(getSkuProperties(sku));
        }
        return new ProductDetail(
                getProductProperties(product),
                keys,
                values,
                skuPropertiesList,
                getPictureUrlList(product.getPreview_id()),
                getPictureUrlList(product.getDetail_id())
        );
    }




}
