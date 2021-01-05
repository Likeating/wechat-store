package com.fortwelve.wechatstore.service.Impl;

import com.fortwelve.wechatstore.dao.*;
import com.fortwelve.wechatstore.dto.*;
import com.fortwelve.wechatstore.pojo.*;
import com.fortwelve.wechatstore.service.ProductService;
import com.fortwelve.wechatstore.service.PropertyService;
import com.fortwelve.wechatstore.component.MyException;
import com.fortwelve.wechatstore.util.ProductUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.*;

@Slf4j
@Transactional(isolation = Isolation.REPEATABLE_READ,rollbackFor = Exception.class)
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

    /**
     * 工具方法，根据图集id获取图片列表
     * @param id
     * @return
     */
    private LinkedList<Picture> getPicturesById(BigInteger id) {
        //获取图集
        PictureList pictureList = pictureListMapper.getPictureListById(id);
        LinkedList<Picture> pictureUrlList = new LinkedList<>();
        if(null == pictureList || null == pictureList.getPictures_id()){
            return null;
        }
        String [] pictureIdArray = pictureList.getPictures_id().split("_");
        for(String pid : pictureIdArray){
            if(pid.equals("")){
                continue;
            }
            pictureUrlList.add(pictureMapper.getPictureById(new BigInteger(pid)));
        }
        return pictureUrlList;
    }
    @Override
    public ProductDetailDTO getProductDetail(BigInteger product_id) throws MyException {
        ProductDetailDTO productDetailDTO = new ProductDetailDTO();
        Product product = productMapper.getProductById(product_id);
        //商品不存在
        if (product==null){
            throw new MyException("商品不存在。",608);
        }
        //商品下架
        if(product.getDelete_time()!=null && System.currentTimeMillis()<product.getDelete_time().getTime()){
            throw new MyException("商品已下架。",607);
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
        LinkedList<Picture> preview_list = getPicturesById(product.getPreview_id());
        LinkedList<String> preview = new LinkedList<>();
        for(Picture tmp : preview_list){
            preview.add(tmp.getUrl());
        }
        LinkedList<Picture> detail_list = getPicturesById(product.getDetail_id());
        LinkedList<String> details = new LinkedList<>();
        for(Picture tmp : detail_list){
            details.add(tmp.getUrl());
        }
        return new ProductDetailDTO(
                getProductProperties(product),
                keys,
                values,
                skuPropertiesDTOList,
                preview,
                details
        );
    }

    @Override
    public void addProduct(ProductDTO productDTO) throws MyException {
        changeProduct(productDTO,false);

    }


    @Override
    public void updateProduct(ProductDTO productDTO) throws MyException {
        changeProduct(productDTO,true);
    }

    /**
     * 增加或修改商品
     * @param productDTO
     * @param update 更新，true表示修改，false表示添加
     * @throws MyException
     */
    public void changeProduct(ProductDTO productDTO,boolean update) throws MyException {
        try{
            Product product = ProductUtils.getProductByProductDTO(productDTO);
            Product product_old = null;
            if(update){
                product_old = productMapper.getProductById(productDTO.getProduct_id());
                if(null == product_old){
                    throw new MyException("商品保存出错：原商品不存在。",631);
                }
                //删除不需要的sku
                List<Sku> skuList_old = skuMapper.getSkuByProductId(product_old.getProduct_id());
                boolean isExist;
                for(Sku tmp_old:skuList_old){
                    if(tmp_old.getSku_id() == null){
                        continue;
                    }
                    isExist = false;
                    List<SkuDTO> skuList_new = productDTO.getSku_list();
                    for(SkuDTO tmp_new : skuList_new){
                        if(null != tmp_new.getSku_id() && tmp_new.getSku_id().equals(tmp_old.getSku_id())){
                           isExist = true;
                        }
                    }
                    if(!isExist){
                        //这里就一定要抛出异常，不然可能会导致获取商品时获取到旧的sku。
                        if(0 == skuMapper.deleteSkuById(tmp_old.getSku_id())){
                            throw new MyException("商品保存出错：sku修改失败。",631);
                        }
                    }
                }

                //删除原有的key
                List<PropertyKey> propertyKeyList = propertyKeyMapper.getAllPropertyKeyByProduct_id(product_old.getProduct_id());
                for(PropertyKey tmp : propertyKeyList){
                    if(0 == propertyKeyMapper.deletePropertyKeyById(tmp.getKey_id())){
                        throw new MyException("商品保存出错：key设置失败。",631);
                    }
                }
                //删除原有的value
                List<PropertyValue> propertyValueList = propertyValueMapper.getAllPropertyValueByProduct_id(product_old.getProduct_id());
                for(PropertyValue tmp : propertyValueList){
                    if(0 == propertyValueMapper.deletePropertyValueById(tmp.getValue_id())){
                        throw new MyException("商品保存出错：key设置失败。",631);
                    }
                }
                //设置销量
                product.setSale(product_old.getSale());
                productDTO.setSale(product_old.getSale());
            }

            //获取主图、轮播图、详情图
            Picture picture_main = productDTO.getPicture_main();
            LinkedList<Picture> picture_preview = productDTO.getPicture_preview();
            LinkedList<Picture> picture_detail = productDTO.getPicture_detail();
            String preview = null;
            String detail = null;

            //设置轮播图
            for(Picture tmp : picture_preview){
                if(null == pictureMapper.getPictureById(tmp.getPicture_id())){
                    throw new MyException("商品保存出错：轮播图设置错误。",631);
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
                throw new MyException("商品保存出错：轮播图保存失败。",631);
            }
            //设置详情图
            for(Picture tmp : picture_detail){
                if(null == pictureMapper.getPictureById(tmp.getPicture_id())){
                    throw new MyException("商品保存出错：详情图设置错误。",631);
                }
                if(null == detail){
                    detail = String.valueOf(tmp.getPicture_id());
                }else {
                    detail = detail +"_"+String.valueOf(tmp.getPicture_id());
                }
            }
            PictureList pictureList_detail = new PictureList();
            pictureList_detail.setPictures_id(detail);
            if(0 == pictureListMapper.addPictureList(pictureList_detail)){
                throw new MyException("商品保存出错：详情图保存失败。",631);
            }

            //设置主图、轮播图、详情图
            product.setPicture_id(picture_main.getPicture_id());
            product.setPreview_id(pictureList_preview.getId());
            product.setDetail_id(pictureList_detail.getId());
            //设置上架时间
            //保存商品
            if(update){
                if(0 == productMapper.updateProduct(product)){
                    throw new MyException("商品保存出错：主信息修改失败。",631);
                }
                //删除原先的图片集

                if(null != product_old.getPreview_id()){
                    if(0 == pictureListMapper.deletePictureListById(product_old.getPreview_id())){
                        throw new MyException("商品保存出错：轮播图设置失败。",631);
//                        log.info("商品保存出错：旧轮播图删除出错。");
                    }
                }
                if(null != product_old.getDetail_id()){
                    if(0 == pictureListMapper.deletePictureListById(product_old.getDetail_id())){
                        throw new MyException("商品保存出错：详情图设置失败。",631);
//                        log.info("商品保存出错：旧详情图删除出错。");
                    }
                }

            }else {
                product.setSale(0);
                if(0 == productMapper.addProduct(product)){
                    throw new MyException("商品保存出错：主信息保存失败。",631);
                }
            }

            //保存key和value
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
                propertyKey.setProduct_id(product.getProduct_id());
                propertyKey.setKey_name(entry.getValue());
                if(0 == propertyService.addPropertyKey(propertyKey)){
                    throw new MyException("商品保存出错：key保存失败。",631);
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
                propertyValue.setProduct_id(product.getProduct_id());
                propertyValue.setValue_name(entry.getValue());
                if(0 == propertyService.addPropertyValue(propertyValue)){
                    throw new MyException("商品保存出错：value保存失败。",631);
                }
                valueId.put(entry.getKey(),String.valueOf(propertyValue.getValue_id()));
                valueMap_new.put(String.valueOf(propertyValue.getValue_id()),entry.getValue());
            }
            productDTO.setValues(valueMap_new);


            //保存sku
            List<SkuDTO> skuDTOList = productDTO.getSku_list();
            for(SkuDTO skuDTO : skuDTOList){
                Sku sku = new Sku();
                sku.setSku_id(skuDTO.getSku_id());
                sku.setProduct_id(product.getProduct_id());



                //把sku属性值替换成实际的keyId_valueId值
                String properties = skuDTO.getProperties();
                String properties_new = null;
                String [] kvs = properties.split("_");
                for(String tmp : kvs){
                    String kv [] = tmp.split(":");
                    String kid = keyId.get(kv[0]);
                    String vid = valueId.get(kv[1]);
                    if(null == kid || null == vid){
                        throw new MyException("商品保存出错：sku属性不正确。",631);
                    }
                    if(null == properties_new){
                        properties_new = kid+":"+vid;
                    }else{
                        properties_new = properties_new+"_"+kid+":"+vid;
                    }
                }
                sku.setProperties(properties_new);

                sku.setSku_price(skuDTO.getSku_price());
                sku.setStock(skuDTO.getStock());
//                if(){
//
//                }
                sku.setPicture_id(skuDTO.getPicture().getPicture_id());

                if(update && null != sku.getSku_id()){
                    Sku sku_old = skuMapper.getSkuById(sku.getSku_id());
                    if(null == sku_old || !product.getProduct_id().equals(sku_old.getProduct_id()) ){
                        System.out.println(sku_old);
                        System.out.println(product.getProduct_id());
                        System.out.println(sku_old.getProduct_id());
                        throw new MyException("商品保存出错：sku属性不正确。",631);
                    }
                    if(0 == skuMapper.updateSku(sku)){
                        throw new MyException("商品保存出错：sku修改失败。",631);
                    }
                }else{
                    if(0 == skuMapper.addSku(sku)){
                        throw new MyException("商品保存出错：sku保存失败。",631);
                    }
                }

            }
            productDTO.setProduct_id(product.getProduct_id());
        }catch (MyException e){
            throw e;
        }catch (Exception e){
            e.printStackTrace();
            throw new MyException("商品保存出错。",631);
        }
    }

    @Override
    public ProductDTO getProductByProduct_id(BigInteger product_id) throws MyException {
        ProductDTO productDTO = null ;
        try{
            Product product = productMapper.getProductById(product_id);
            if(null == product){
                throw new MyException("商品不存在。",608);
            }
            productDTO = ProductUtils.getProductDTOByProduct(product);
            productDTO.setPicture_main(pictureMapper.getPictureById(product.getPicture_id()));
            productDTO.setPicture_preview(getPicturesById(product.getPreview_id()));
            productDTO.setPicture_detail(getPicturesById(product.getDetail_id()));
            List<Sku> skuList = skuMapper.getSkuByProductId(product_id);
            List<SkuDTO> skuDTOList = new LinkedList<>();

            HashMap<String,String> keys = new HashMap<>();
            HashMap<String,String> values = new HashMap<>();
            Iterator<Sku> iterator = skuList.iterator();

            while (iterator.hasNext()){
                Sku sku = iterator.next();
                String [] properties=sku.getProperties().split("_");
                for(String property:properties){
                    String [] kv = property.split(":");
                    if(keys.get(kv[0])==null){
                        PropertyKey propertyKey = propertyService.getPropertyKeyById(new BigInteger(kv[0]));
                        if(propertyKey == null){
                            throw new MyException("商品获取失败：sku属性名不正确。",632);
//                            iterator.remove();
//                            break;
                        }
                        keys.put(kv[0],propertyKey.getKey_name());
                    }
                    if(values.get(kv[1])==null){
                        PropertyValue propertyValue = propertyService.getPropertyValueById(new BigInteger(kv[1]));
                        if(propertyValue == null){
                            throw new MyException("商品获取失败：sku属性值不正确。",632);
//                            iterator.remove();
//                            break;
                        }
                        values.put(kv[1],propertyValue.getValue_name());
                    }
                }
                SkuDTO skuDTO = new SkuDTO();
                skuDTO.setSku_id(sku.getSku_id());
                skuDTO.setProduct_id(sku.getProduct_id());
                skuDTO.setProperties(sku.getProperties());
                skuDTO.setSku_price(sku.getSku_price());
                skuDTO.setStock(sku.getStock());
                skuDTO.setPicture(pictureMapper.getPictureById(sku.getPicture_id()));

                skuDTOList.add(skuDTO);
            }
            productDTO.setKeys(keys);
            productDTO.setValues(values);
            productDTO.setSku_list(skuDTOList);

        }catch (MyException e){
            throw e;
        }catch (Exception e){
            e.printStackTrace();
            throw new MyException("商品获取失败。",632);
        }

        return productDTO;
    }
}
