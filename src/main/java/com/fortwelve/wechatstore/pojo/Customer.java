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
public class Customer {

  private BigInteger customer_id;
  private String customer_name;

}
