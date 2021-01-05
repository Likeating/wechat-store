package com.fortwelve.wechatstore.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PropertyValue {

  private BigInteger value_id;
  private BigInteger product_id;
  private String value_name;
  private BigInteger picture_id;

}
