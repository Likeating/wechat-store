package com.fortwelve.wechatstore.dto;

import com.fortwelve.wechatstore.controller.ValidatedGroup.addProduct;
import com.fortwelve.wechatstore.controller.ValidatedGroup.updateProduct;
import com.fortwelve.wechatstore.pojo.Picture;
import com.fortwelve.wechatstore.pojo.Sku;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductDTO {

    //由于validated分组校验不能检验嵌套对象，所以只能放弃分组功能
//    @NotNull (message = "商品ID不能为空。")
    private BigInteger product_id;

    @NotBlank(message = "商品名不能为空。")
    private String product_name;

    @NotNull(message = "分类id不能为空。")
    private Integer category_id;

    @NotNull(message = "价格不能为空。")
    private BigDecimal price;

    @NotNull(message = "上架时间不能为空。")
    private Timestamp add_time;

    private Timestamp delete_time;

    private String state;

    private Integer sale;

    @Valid
    @NotNull(message = "商品主图不能为空。")
    private Picture picture_main;
    @Valid
    @NotNull(message = "商品轮播图不能为空。")
    private LinkedList<Picture> picture_preview;
    @Valid
    @NotNull(message = "商品详情图不能为空。")
    private LinkedList<Picture> picture_detail;

    @NotNull(message = "属性键不能为空。")
    private HashMap<String,String> keys;
    @NotNull(message = "属性值不能为空。")
    private HashMap<String,String> values;
    @Valid
    @NotNull(message = "sku列表不能为空。")
    private List<SkuDTO> sku_list;

}
