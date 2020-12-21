package com.fortwelve.wechatstore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.math.BigInteger;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SkuPropertiesDTO {

    private BigInteger sku_id;
    private BigInteger product_id;
    private String properties;
    private BigDecimal sku_price;
    private int stock;
    private String url;

}
