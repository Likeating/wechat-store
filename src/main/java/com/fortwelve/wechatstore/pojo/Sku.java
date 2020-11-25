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

  private long skuId;
  private long productId;
  private String entries;
  private double skuPrice;
  private long pictureId;

}
