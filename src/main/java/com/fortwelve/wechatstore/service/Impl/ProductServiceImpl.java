package com.fortwelve.wechatstore.service.Impl;

import com.fortwelve.wechatstore.dao.*;
import com.fortwelve.wechatstore.dto.ProductDTO;
import com.fortwelve.wechatstore.dto.ProductDetailDTO;
import com.fortwelve.wechatstore.dto.ProductPropertiesDTO;
import com.fortwelve.wechatstore.dto.SkuPropertiesDTO;
import com.fortwelve.wechatstore.pojo.*;
import com.fortwelve.wechatstore.service.ProductService;
import com.fortwelve.wechatstore.service.PropertyService;
import com.fortwelve.wechatstore.util.OrderException;
import com.fortwelve.wechatstore.util.ProductUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.*;

@Transactional(rollbackFor = Exception.class)
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
    @Autowired
    PropertyService propertyService;

    @Override
    public int addProduct(Product product) {
        return productMapper.addProduct(product);
    }

    @Override
    public Product getProductById(BigInteger id) {
        return productMapper.getProductById(id);
    }

    @Override
    public List<Product> getAllProduct() {
        return productMapper.getAllProduct();
    }

    @Override
    public List<Product> getProductPage(int offset, int rows) {
        return productMapper.getProductPage(offset,rows);
    }

    @Override
    public int deleteProductById(BigInteger id) {
        return productMapper.deleteProductById(id);
    }

    @Override
    public int updateProduct(Product product) {
        return productMapper.updateProduct(product);
    }

    //sku start
    //sku start
    //sku start
    @Override
    public int addSku(Sku sku) {
        return skuMapper.addSku(sku);
    }

    @Override
    public int updateSku(Sku sku) {
        return skuMapper.updateSku(sku);
    }

    @Override
    public int deleteSkuById(BigInteger id) {
        return skuMapper.deleteSkuById(id);
    }

    @Override
    public Sku getSkuById(BigInteger id) {
        return skuMapper.getSkuById(id);
    }

    @Override
    public List<Sku> getAllSku() {
        return skuMapper.getAllSku();
    }

    @Override
    public List<Sku> getSkuByProductId(BigInteger product_id) {
        return skuMapper.getSkuByProductId(product_id);
    }

    @Override
    public int getStockByProductId(BigInteger product_id) {
        return skuMapper.getStockByProductId(product_id);
    }

    //sku end
    //sku end
    //sku end


    @Override
    public ProductPropertiesDTO getProductProperties(Product product) {
        return new ProductPropertiesDTO(product.getProduct_id(),
                product.getProduct_name(),
                product.getPrice(),
                product.getCategory_id(),
                product.getAdd_time(),
                product.getDelete_time(),
                product.getState(),
                pictureMapper.getPictureById(product.getPicture_id()).getUrl(),
                skuMapper.getStockByProductId(product.getProduct_id()),
                product.getSale()
        );
    }

    @Override
    public SkuPropertiesDTO getSkuProperties(Sku sku) {
        return new SkuPropertiesDTO(sku.getSku_id(),
                sku.getProduct_id(),
                sku.getProperties(),
                sku.getSku_price(),
                sku.getStock(),
                pictureMapper.getPictureById(sku.getPicture_id()).getUrl());
    }

    @Override
    public List<Product> searchProductPage(List<String> keywords,Integer cid,Integer sort,Integer offset, Integer rows){
        List<Product> products = productMapper.searchProductPage(keywords,cid,sort,offset,rows);
        return products;
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
    public ProductDetailDTO getProductDetail(BigInteger product_id) throws OrderException {
        ProductDetailDTO productDetailDTO = new ProductDetailDTO();
        Product product = productMapper.getProductById(product_id);
        //商品不存在
        if (product==null){
            throw new OrderException("商品不存在。",608);
        }
        //商品下架
        if(product.getDelete_time()!=null && System.currentTimeMillis()<product.getDelete_time().getTime()){
            throw new OrderException("商品已下架。",607);
        }

        //获取sku属性、key、value
        List<Sku> skuList = skuMapper.getSkuByProductId(product.getProduct_id());
        List<SkuPropertiesDTO> skuPropertiesDTOList = new LinkedList<>();

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
            skuPropertiesDTOList.add(getSkuProperties(sku));
        }
        return new ProductDetailDTO(
                getProductProperties(product),
                keys,
                values,
                skuPropertiesDTOList,
                getPictureUrlList(product.getPreview_id()),
                getPictureUrlList(product.getDetail_id())
        );
    }

    @Override
    public void addProduct(ProductDTO productDTO) throws OrderException {

        try{
            HashMap<String,String> keyMap = productDTO.getKeys();
            HashMap<String,String> valueMap = productDTO.getValues();

            HashMap<String,String> keyId =  new HashMap<>();
            HashMap<String,String> valueId = new HashMap<>();
            HashMap<String,String> keyMap_new =  new HashMap<>();
            HashMap<String,String> valueMap_new = new HashMap<>();

            //把key保存到数据库
            Iterator<Map.Entry<String,String>> iterator = keyMap.entrySet().iterator();
            Map.Entry<String,String> entry;
            while(iterator.hasNext()){
                entry = iterator.next();
                PropertyKey propertyKey = new PropertyKey();
                propertyKey.setKey_name(entry.getValue());
                if(0 == propertyService.addPropertyKey(propertyKey)){
                    throw new OrderException("商品添加失败：key保存失败。",631);
                }
                keyId.put(entry.getKey(),String.valueOf(propertyKey.getKey_id()));
                keyMap_new.put(String.valueOf(propertyKey.getKey_id()),entry.getValue());
            }
            productDTO.setKeys(keyMap_new);
            //把value保存到数据库
            iterator = valueMap.entrySet().iterator();
            while(iterator.hasNext()){
                entry = iterator.next();
                PropertyValue propertyValue = new PropertyValue();
                propertyValue.setValue_name(entry.getValue());
                if(0 == propertyService.addPropertyValue(propertyValue)){
                    throw new OrderException("商品添加失败：value保存失败。",631);
                }
                valueId.put(entry.getKey(),String.valueOf(propertyValue.getValue_id()));
                valueMap_new.put(String.valueOf(propertyValue.getValue_id()),entry.getValue());
            }
            productDTO.setValues(valueMap_new);
            //获取主图、轮播图、详情图
            Picture picture_main = productDTO.getPicture_main();
            LinkedList<Picture> picture_preview = productDTO.getPicture_preview();
            LinkedList<Picture> picture_detail = productDTO.getPicture_detail();
            String preview = null;
            String detail = null;

            //设置轮播图
            for(Picture tmp : picture_preview){
                if(null == pictureMapper.getPictureById(tmp.getPicture_id())){
                    throw new OrderException("商品添加失败：轮播图设置错误。",631);
                }
                if(null == preview){
                    preview = String.valueOf(tmp.getPicture_id());
                }else {
                    preview = preview +"_"+String.valueOf(tmp.getPicture_id());
                }
            }
            PictureList pictureList_preview = new PictureList();
            pictureList_preview.setPictures_id(preview);

            if(0 == pictureListMapper.addPictureList(pictureList_preview)){
                throw new OrderException("商品添加失败：轮播图保存失败。",631);
            }
            //设置详情图
            for(Picture tmp : picture_detail){
                if(null == pictureMapper.getPictureById(tmp.getPicture_id())){
                    throw new OrderException("商品添加失败：详情图设置错误。",631);
                }
                if(null == detail){
                    detail = String.valueOf(tmp.getPicture_id());
                }else {
                    detail = detail +"_"+String.valueOf(tmp.getPicture_id());
                }
            }
            PictureList pictureList_detail = new PictureList();
            pictureList_detail.setPictures_id(preview);
            if(0 == pictureListMapper.addPictureList(pictureList_detail)){
                throw new OrderException("商品添加失败：详情图保存失败。",631);
            }
            Product product = ProductUtils.getProductByProductDTO(productDTO);
            //设置主图、轮播图、详情图
            product.setPicture_id(picture_main.getPicture_id());
            product.setPreview_id(pictureList_preview.getId());
            product.setDetail_id(pictureList_detail.getId());
            //保存商品
            if(0 == productMapper.addProduct(product)){
                throw new OrderException("商品添加失败：主信息保存失败。",631);
            }

            //保存sku
            List<Sku> skuList = productDTO.getSku_list();
            for(Sku sku : skuList){
                sku.setProduct_id(product.getProduct_id());

                //把sku属性值替换成实际的keyId_valueId值
                String properties = sku.getProperties();
                String properties_new = null;
                String [] kvs = properties.split("_");
                for(String tmp : kvs){
                    String kv [] = tmp.split(":");
                    String kid = keyId.get(kv[0]);
                    String vid = valueId.get(kv[1]);
                    if(null == kid || null == vid){
                        throw new OrderException("商品添加失败：sku属性不正确。",631);
                    }
                    if(null == properties_new){
                        properties_new = kid+":"+vid;
                    }else{
                        properties_new = properties_new+"_"+kid+":"+vid;
                    }
                }
                sku.setProperties(properties_new);
                if(skuMapper.addSku(sku)==0){
                    throw new OrderException("商品添加失败：sku保存失败。",631);
                }
            }
            productDTO.setProduct_id(product.getProduct_id());
        }catch (OrderException e){
            throw e;
        }catch (Exception e){
            throw new OrderException("商品添加失败。",631);
        }

    }
}
