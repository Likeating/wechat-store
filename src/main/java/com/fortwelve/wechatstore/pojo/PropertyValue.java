package com.fortwelve.wechatstore.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PropertyValue {

  private java.math.BigInteger value_id;
  private String value_name;
  private java.math.BigInteger picture_id;

}
