package com.fortwelve.wechatstore.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Sku {

  private java.math.BigInteger sku_id;
  private java.math.BigInteger product_id;
  private String entries;
  private java.math.BigDecimal sku_price;
  private java.math.BigInteger picture_id;

}
