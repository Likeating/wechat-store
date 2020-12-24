package com.fortwelve.wechatstore.controller;

import com.fortwelve.wechatstore.component.MsgMap;
import com.fortwelve.wechatstore.controller.ValidatedGroup.addProduct;
import com.fortwelve.wechatstore.dto.ProductDTO;
import com.fortwelve.wechatstore.pojo.Product;
import com.fortwelve.wechatstore.pojo.PropertyKey;
import com.fortwelve.wechatstore.pojo.Sku;
import com.fortwelve.wechatstore.service.PictureService;
import com.fortwelve.wechatstore.service.ProductService;
import com.fortwelve.wechatstore.service.PropertyService;
import com.fortwelve.wechatstore.util.OrderException;
import com.fortwelve.wechatstore.util.ProductUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;
    @Autowired
    PictureService pictureService;
    @Autowired
    PropertyService propertyService;

    @GetMapping("/search")
    public Object search(String query, Integer cid, Integer sort,Integer pagenum,Integer pagesize){
        MsgMap msg = new MsgMap();

        try{
            String str =null;
            List<String> keywords=null;
            if(query!=null && ! (str = query.trim()).equals("")){//有关键字
                System.out.println(str);
                keywords = Arrays.asList(str.split("\\s+"));
            }
            Integer start = null;
            if(null != pagenum){
                if(pagenum < 1){
                    pagenum = 1;
                }
                start = (pagenum - 1)*5;
            }else {
                pagenum = 1;
            }

            List<Product> productList = productService.searchProductPage(keywords,cid,sort,start,pagesize);
            List<ProductDTO> productDTOList = new LinkedList<>();
            ProductDTO productDTO;
            for (Product tmp : productList){
                productDTO = ProductUtils.getProductDTOByProduct(tmp);
                productDTO.setPicture_main(pictureService.getPictureById(tmp.getPicture_id()));
//                productDTO.setPicture_preview(pictureService.getPicturesById(tmp.getPreview_id()));
//                productDTO.setPicture_detail(pictureService.getPicturesById(tmp.getDetail_id()));
                productDTOList.add(productDTO);
            }
            int totalNum = productService.searchProductPage(keywords,cid,sort,null,null).size();
            msg.setMeta("获取成功。",200);
            msg.put("pagenum",pagenum);
            msg.put("total",totalNum);
            msg.put("productList",productDTOList);
        }catch (Exception e){
            e.printStackTrace();
            log.info("/product/search出错："+e.getMessage());
            msg.setMeta("服务器出错。",500);
        }
        return msg;
    }


    @GetMapping("/detail")
    public Object detail(@RequestParam BigInteger product_id){
        MsgMap msg = new MsgMap();

        try{
            Product product = productService.getProductById(product_id);
            if(null == product){
                msg.setMeta("商品不存在。",608);
                return msg;
            }
            ProductDTO productDTO = ProductUtils.getProductDTOByProduct(product);
            productDTO.setPicture_main(pictureService.getPictureById(product.getPicture_id()));
            productDTO.setPicture_preview(pictureService.getPicturesById(product.getPreview_id()));
            productDTO.setPicture_detail(pictureService.getPicturesById(product.getDetail_id()));
            List<Sku> skuList = productService.getSkuByProductId(product_id);


            HashMap<String,String> keys = new HashMap<>();
            HashMap<String,String> values = new HashMap<>();
            for (Sku sku : skuList){
                String [] properties=sku.getProperties().split("_");
                for(String property:properties){
                    String [] kv = property.split(":");
                    if(keys.get(kv[0])==null){
                        keys.put(kv[0],propertyService.getPropertyKeyById(new BigInteger(kv[0])).getKey_name());
                    }
                    if(values.get(kv[1])==null){
                        values.put(kv[1],propertyService.getPropertyValueById(new BigInteger(kv[1])).getValue_name());
                    }
                }
            }
            productDTO.setKeys(keys);
            productDTO.setValues(values);
            productDTO.setSku_list(skuList);

            msg.setMeta("获取成功。",200);
            msg.put("product",productDTO);

        }catch (Exception e){
            e.printStackTrace();
            log.info("/product/search出错："+e.getMessage());
            msg.setMeta("服务器出错。",500);
        }
        return msg;
    }

    @PostMapping("/addProduct")
    public Object addProduct(@Validated(addProduct.class) @RequestBody ProductDTO productDTO, BindingResult result){
        MsgMap msg = new MsgMap();

        try{
            //验证字段
            if (result.hasErrors()){
                msg.setMeta(result.getFieldError().getDefaultMessage(),701);
                return msg;
            }
            productService.addProduct(productDTO);
            msg.put("product",productDTO);
            msg.setMeta("添加成功。",200);
        }catch (OrderException e){
            e.printStackTrace();
            log.info("/product/addProduct出错："+e.getMessage());
            msg.setMeta(e.getMessage(),e.getCode());
        }catch (Exception e){
            e.printStackTrace();
            log.info("/product/addProduct出错："+e.getMessage());
            msg.setMeta("服务器出错。",500);
        }
        return msg;
    }
}
