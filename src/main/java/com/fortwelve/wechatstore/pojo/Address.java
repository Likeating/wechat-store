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
public class Address {

  private BigInteger address_id;
  private BigInteger customer_id;
  private String recipient;
  private String telnumber;
  private String address_detail;

}
