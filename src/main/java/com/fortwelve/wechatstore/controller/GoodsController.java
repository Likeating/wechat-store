package com.fortwelve.wechatstore.controller;


import com.fortwelve.wechatstore.bean.ProductProperties;
import com.fortwelve.wechatstore.bean.SkuProperties;
import com.fortwelve.wechatstore.pojo.*;
import com.fortwelve.wechatstore.service.*;
import com.fortwelve.wechatstore.util.GoodsFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.math.BigInteger;
import java.util.*;

@RestController
@RequestMapping("/goods/")
public class GoodsController {

    @Autowired
    ProductService productService;
    @Autowired
    PictureService pictureService;
    @Autowired
    PictureListService pictureListService;
    @Autowired
    SkuService skuService;
    @Autowired
    GoodsFactory goodsFactory;
    @Autowired
    PropertyKeyService propertyKeyService;
    @Autowired
    PropertyValueService propertyValueService;

    @RequestMapping("/search")
    public Object search(String query,String cid,int pagenum,int pagesize){
        Map<String,Object> map = new HashMap<>();
        Map<String,Object> meta = new HashMap<>();
        Map<String,Object> msg = new HashMap<>();

        try{
            String str = query.trim();
            if(str==null || str.equals("")){
                throw new Exception();
            }
            List<String> keywords =Arrays.asList(str.split("\\s+"));

            List<Product> products = productService.searchProductPage(keywords,pagenum,pagesize);

            meta.put("msg","获取成功");
            meta.put("status",200);

            msg.put("pagenum",pagenum);
            String url;
            ProductProperties goods;
            List<ProductProperties> goodsList = new LinkedList<>();
            for (Product product : products){
                goods = goodsFactory.getProductProperties(product,pictureService.getPictureById(product.getPicture_id()),100);
                goodsList.add(goods);
            }
            msg.put("total",goodsList.size());
            msg.put("goods",goodsList);
            map.put("message",msg);
        }catch (Exception e){
            meta.put("msg","获取失败");
            meta.put("status",400);//随便定义的
        }
        map.put("meta",meta);
        return map;
    }
    @RequestMapping("/detail")
    public Object detail(Integer goods_id) {
        Map<String,Object> map = new HashMap<>();
        Map<String,Object> meta = new HashMap<>();
        Map<String,Object> msg = new HashMap<>();
        List<String> previews = new LinkedList<>();
        List<String> pics = new LinkedList<>();
        Map<String,String> keys = new HashMap<>();
        Map<String,String> values = new HashMap<>();
        List<SkuProperties> skuPropertiesList = new LinkedList<>();
        SkuProperties skuProperties;
        int stock = 0;
        try {

            Product product=productService.getProductById(BigInteger.valueOf(goods_id));
            PictureList previewList = pictureListService.getProductDetailById(product.getPreview_id());
            PictureList detailList = pictureListService.getProductDetailById(product.getDetail_id());

            //获取预览图集
            String [] previewArray = previewList.getPictures_id().split("_");
            if (previewArray.length>0){
                for(String pid : previewArray){
                    previews.add(pictureService.getPictureById(new BigInteger(pid)).getUrl());
                }
            }
            //获取商品详情图集
            String [] detailArray = detailList.getPictures_id().split("_");
            if (detailArray.length>0){
                for(String pid : detailArray){
                    pics.add(pictureService.getPictureById(new BigInteger(pid)).getUrl());
                }
            }

            //获取sku
            List<Sku> skuList = skuService.getSkuByProductId(product.getProduct_id());
            String [] propertiesArray;
            String [] kv;
            String key,value;
            for(Sku sku:skuList){
                propertiesArray=sku.getProperties().split("_");
                for(String property:propertiesArray){
                    kv = property.split(":");
                    if(keys.get(kv[0])==null){
                        key = propertyKeyService.getPropertyKeyById(new BigInteger(kv[0])).getKey_name();
                        keys.put(kv[0],key);
                    }
                    if(values.get(kv[1])==null){
                        value = propertyValueService.getPropertyValueById(new BigInteger(kv[1])).getValue_name();
                        values.put(kv[1],value);
                    }
                }
                skuProperties = goodsFactory.getSkuProperties(sku,pictureService.getPictureById(sku.getPicture_id()));
                skuPropertiesList.add(skuProperties);
                stock += sku.getStock();
            }
            ProductProperties goods = goodsFactory.getProductProperties(product,pictureService.getPictureById(product.getPicture_id()),stock);
            msg.put("skuList",skuPropertiesList);
            msg.put("keyList",keys);
            msg.put("valueList",values);

            msg.put("goods",goods);
            msg.put("previews",previews);
            msg.put("pics",pics);

            meta.put("msg","获取成功");
            meta.put("status",200);
        }catch (Exception e){
            meta.put("msg","获取失败");
            meta.put("status",400);
        }
        map.put("meta",meta);
        map.put("msg",msg);
        return map;
    }

}
