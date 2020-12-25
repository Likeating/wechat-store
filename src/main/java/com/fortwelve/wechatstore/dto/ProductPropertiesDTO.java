package com.fortwelve.wechatstore.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductPropertiesDTO {
    private BigInteger goods_id;
    private String goods_name;
    private BigDecimal goods_price;
    private int cat_id;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Timestamp add_time;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Timestamp delete_time;
    private String state;//描述
    private String picture_url;//主图
    private int goods_number;//库存
    private int sale;//销量
}
