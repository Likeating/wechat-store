package com.fortwelve.wechatstore.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Product {

  private java.math.BigInteger product_id;
  private String product_name;
  private java.math.BigDecimal price;
  private int category_id;
  private java.sql.Timestamp add_time;
  private java.sql.Timestamp delete_time;
  private String state;
  private java.math.BigInteger picture_id;

}
