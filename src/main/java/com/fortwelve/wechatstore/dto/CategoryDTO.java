package com.fortwelve.wechatstore.dto;

import com.fortwelve.wechatstore.pojo.Picture;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CategoryDTO {
    private Integer category_id;
    @NotNull(message = "分类名不能为空。")
    private String category_name;
    @NotNull(message = "图片不能为空。")
    private Picture picture;
}
