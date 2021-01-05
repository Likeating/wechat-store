package com.fortwelve.wechatstore.controller;

import com.auth0.jwt.interfaces.Claim;
import com.fortwelve.wechatstore.component.MsgMap;
import com.fortwelve.wechatstore.dto.ProductDTO;
import com.fortwelve.wechatstore.pojo.Product;
import com.fortwelve.wechatstore.service.PictureService;
import com.fortwelve.wechatstore.service.ProductService;
import com.fortwelve.wechatstore.service.PropertyService;
import com.fortwelve.wechatstore.util.JWTUtils;
import com.fortwelve.wechatstore.component.MyException;
import com.fortwelve.wechatstore.util.ProductUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.math.BigInteger;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/productManage")
public class ProductManageController {
    @Autowired
    ProductService productService;
    @Autowired
    PictureService pictureService;
    @Autowired
    PropertyService propertyService;
    @Value("${JWTUtils.manager.signature}")
    private String managerSignature;
    @Value("${JWTUtils.manager.minute}")
    private int managerMinute;

    @GetMapping("/search")
    public Object search(String keys, Integer cid, Integer sort,Integer currentPage,Integer pageSize){
        MsgMap msg = new MsgMap();
        try{
            //关键字预处理
            String str =null;
            List<String> keywords=null;
            if(keys!=null && ! (str = keys.trim()).equals("")){//有关键字
                System.out.println(str);
                keywords = Arrays.asList(str.split("\\s+"));
            }
            //分类功能预处理
            Integer start = null;
            if(null != currentPage){
                if(currentPage < 1){
                    currentPage = 1;
                }
                start = (currentPage - 1)*pageSize;
            }else{
                currentPage = 1;
            }
            //产品搜索
            List<Product> productList = productService.searchProductPage(keywords,cid,sort,start,pageSize);
            //将其转化成前端所需要的dto
            List<ProductDTO> productDTOList = new LinkedList<>();
            ProductDTO productDTO;
            for (Product tmp : productList){
                productDTO = ProductUtils.getProductDTOByProduct(tmp);
                productDTO.setPicture_main(pictureService.getPictureById(tmp.getPicture_id()));
                productDTOList.add(productDTO);
            }
            int totalNum = productService.searchProductPage(keywords,cid,sort,null,null).size();
//            int totalNum = productDTOList.size();
            //返回数据
            msg.setMeta("获取成功。",200);
            msg.put("currentPage",currentPage);
            msg.put("total",totalNum);
            msg.put("productList",productDTOList);
        }catch (Exception e){
            e.printStackTrace();
            log.debug("/product/search出错："+e.getMessage());
            msg.setMeta("服务器出错。",500);
        }
        return msg;
    }


    @GetMapping("/detail")
    public Object detail(@RequestParam BigInteger product_id){
        MsgMap msg = new MsgMap();

        try{
            ProductDTO productDTO = productService.getProductByProduct_id(product_id);
            msg.setMeta("获取成功。",200);
            msg.put("product",productDTO);

        }catch (MyException e){
            log.debug("/product/search出错："+e.getMessage());
            msg.setMeta(e.getMessage(),e.getCode());
        }catch (Exception e){
            e.printStackTrace();
            log.debug("/product/search出错："+e.getMessage());
            msg.setMeta("服务器出错。",500);
        }
        return msg;
    }

    @PostMapping("/addProduct")
    public Object addProduct(@Valid @RequestBody ProductDTO productDTO, BindingResult result, HttpServletRequest request){
        MsgMap msg = new MsgMap();

        try{
            //验证字段
            if (result.hasErrors()){
                msg.setMeta(result.getFieldError().getDefaultMessage(),701);
                return msg;
            }
            //权限判断
            String token = request.getHeader("token");
            Map<String, Claim> tokenMap = JWTUtils.decode(token,managerSignature);
            String role = tokenMap.get("role").asString();
            if(!role.equals("超级管理员") && !role.equals("商品管理员")){
                msg.setMeta("没有权限操作。",611);
                return msg;
            }

            productService.addProduct(productDTO);
            //获取添加后的信息
            productDTO = productService.getProductByProduct_id(productDTO.getProduct_id());
            msg.put("product",productDTO);
            msg.setMeta("添加成功。",200);
        }catch (MyException e){
            e.printStackTrace();
            log.debug("/product/addProduct出错："+e.getMessage());
            msg.setMeta(e.getMessage(),e.getCode());
        }catch (Exception e){
            e.printStackTrace();
            log.debug("/product/addProduct出错："+e.getMessage());
            msg.setMeta("服务器出错。",500);
        }
        return msg;
    }

    @PostMapping("/updateProduct")
    public Object updateProduct(@Valid @RequestBody ProductDTO productDTO, BindingResult result,HttpServletRequest request){
        MsgMap msg = new MsgMap();

        try{
            //验证字段
            if (result.hasErrors()){
                msg.setMeta(result.getFieldError().getDefaultMessage(),701);
                return msg;
            }
            if(productDTO.getProduct_id()==null){
                msg.setMeta("商品ID不能为空。",701);
                return msg;
            }
            //权限判断
            String token = request.getHeader("token");
            Map<String, Claim> tokenMap = JWTUtils.decode(token,managerSignature);
            String role = tokenMap.get("role").asString();
            if(!role.equals("超级管理员") && !role.equals("商品管理员")){
                msg.setMeta("没有权限操作。",611);
                return msg;
            }

            productService.updateProduct(productDTO);
            //获取更新后的信息
            productDTO = productService.getProductByProduct_id(productDTO.getProduct_id());
            msg.put("product",productDTO);
            msg.setMeta("修改成功。",200);
        }catch (MyException e){
            e.printStackTrace();
            log.debug("/product/updateProduct出错："+e.getMessage());
            msg.setMeta(e.getMessage(),e.getCode());
        }catch (Exception e){
            e.printStackTrace();
            log.debug("/product/updateProduct出错："+e.getMessage());
            msg.setMeta("服务器出错。",500);
        }
        return msg;
    }
}
