package com.fortwelve.wechatstore.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PropertyKey {

  private java.math.BigInteger key_id;
  private String key_name;
  private boolean isPicture;

}
