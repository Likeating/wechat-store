package com.fortwelve.wechatstore.dto;

import com.fortwelve.wechatstore.controller.ValidatedGroup.addProduct;
import com.fortwelve.wechatstore.pojo.Picture;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SkuDTO {

  private BigInteger sku_id;
  private BigInteger product_id;
  @NotNull(message = "sku属性不能为空。")
  private String properties;
  @NotNull(message = "sku价格不能为空。")
  private BigDecimal sku_price;
  @NotNull(message = "sku库存不能为空。")
  private Integer stock;
  @Valid
  @NotNull(message = "sku图片不能为空。")
  private Picture picture;
}

