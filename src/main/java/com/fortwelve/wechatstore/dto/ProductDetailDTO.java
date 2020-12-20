package com.fortwelve.wechatstore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductDetailDTO {
    private ProductPropertiesDTO product;//商品详情
    private Map<String,String> keys;//属性
    private Map<String,String> values;//属性值
    private List<SkuPropertiesDTO> skuList;//sku属性
    private List<String> previews;//预览图（轮播图）
    private List<String> pics;//详情图
}
