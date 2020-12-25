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
public class PropertyKey {

  private BigInteger key_id;
  private BigInteger product_id;
  private String key_name;
  private boolean isPicture;

}
