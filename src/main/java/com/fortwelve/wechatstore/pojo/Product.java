package com.fortwelve.wechatstore.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Product {

  private BigInteger product_id;
  private String product_name;
  private BigDecimal price;
  private int category_id;
  private Timestamp add_time;
  private Timestamp delete_time;
  private String state;
  private BigInteger picture_id;
  private BigInteger preview_id;
  private BigInteger detail_id;

}
