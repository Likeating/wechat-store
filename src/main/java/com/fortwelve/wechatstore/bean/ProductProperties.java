package com.fortwelve.wechatstore.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.context.annotation.Scope;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductProperties {
    private BigInteger goods_id;
    private String goods_name;
    private BigDecimal goods_price;
    private int cat_id;
    private int goods_number;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Timestamp add_time;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Timestamp delete_time;
    private String picture_url;
}
