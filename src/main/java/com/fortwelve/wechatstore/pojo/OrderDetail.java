package com.fortwelve.wechatstore.pojo;

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
public class OrderDetail {

  private BigInteger id;
  private BigInteger order_id;
  private BigInteger product_id;
  private BigInteger sku_id;
  private String product_name;
  private String sku_attr;
  private BigDecimal sku_price;
  private int num;
  private String picture;
}
