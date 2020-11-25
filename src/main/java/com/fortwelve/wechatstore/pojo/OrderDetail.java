package com.fortwelve.wechatstore.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderDetail {

  private java.math.BigInteger id;
  private java.math.BigInteger order_id;
  private java.math.BigInteger sku_id;
  private String sku_attr;
  private java.math.BigDecimal sku_price;
  private int num;

}
