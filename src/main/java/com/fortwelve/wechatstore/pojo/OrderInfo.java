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
public class OrderInfo {

  private BigInteger order_id;
  private BigInteger customer_id;
  private BigDecimal total_price;
  private BigDecimal freight_price;
  private BigDecimal pay_price;
  private String address;
  private int order_status;
  private Timestamp create_time;
  private String note;

}
