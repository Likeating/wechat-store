package com.fortwelve.wechatstore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CategoryDTO {
    private int category_id;
    private String category_name;
    private BigInteger picture_id;
}
