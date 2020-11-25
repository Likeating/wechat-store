package com.fortwelve.wechatstore.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderInfo {

  private java.math.BigInteger order_id;
  private java.math.BigInteger customer_id;
  private java.math.BigDecimal total_price;
  private java.math.BigDecimal freight_price;
  private java.math.BigDecimal pay_price;
  private String address;
  private int order_status;
  private java.sql.Timestamp create_time;
  private String note;

}
