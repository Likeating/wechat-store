package com.fortwelve.wechatstore.service;

import com.fortwelve.wechatstore.dto.ProductDTO;
import com.fortwelve.wechatstore.dto.ProductDetailDTO;
import com.fortwelve.wechatstore.dto.ProductPropertiesDTO;
import com.fortwelve.wechatstore.dto.SkuPropertiesDTO;
import com.fortwelve.wechatstore.pojo.Product;
import com.fortwelve.wechatstore.pojo.Sku;
import com.fortwelve.wechatstore.component.MyException;

import java.math.BigInteger;
import java.util.List;

public interface ProductService {

    int addProduct(Product product);
    Product getProductById(BigInteger id);
    List<Product> getAllProduct();
    List<Product> getProductPage(int offset,int rows);
    int deleteProductById(BigInteger id);
    int updateProduct(Product product);


    public int addSku(Sku sku);
    public int updateSku(Sku sku);
    public int deleteSkuById(BigInteger id);
    public Sku getSkuById(BigInteger id);
    public List<Sku> getAllSku();
    public List<Sku> getSkuByProductId(BigInteger product_id);
    public int getStockByProductId(BigInteger product_id);

    /**
     * 模糊查询，返回Product列表
     * @param keywords 关键字
     * @param cid 可以空，按分类查找
     * @param sort 排序方式，null，不排序
     *             价格排序 ：1.desc 降序 2.asc升序
     *             销量排序 ：3.desc 降序 4.asc升序
     * @param offset 起始位置，可以空，但rows为空，此参数也不会生效
     * @param rows 数量，可以空
     * @return List<Product>
     */
    List<Product> searchProductPage(List<String> keywords,Integer cid,Integer sort,Integer offset, Integer rows);


    /**
     * Product转ProductProperties
     * @param product
     * @return ProductPropertiesDTO
     */
    ProductPropertiesDTO getProductProperties(Product product);

    /**
     * Sku转SkuProperties
     * @param sku
     * @return SkuPropertiesDTO
     */
    SkuPropertiesDTO getSkuProperties(Sku sku);

    /**
     * 获取商品详细数据
     * @param product_id
     * @return ProductDetailDTO
     */
    ProductDetailDTO getProductDetail(BigInteger product_id) throws MyException;

    /**
     * 获取图片URL列表
     * @param id
     * @return
     */
//    LinkedList<Picture> getPicturesById(BigInteger id);

    /**
     * 添加商品
     * @param productDTO
     * @throws MyException
     */
    void addProduct(ProductDTO productDTO) throws MyException;

    /**
     * 修改商品
     * @param productDTO
     * @throws MyException
     */
    void updateProduct(ProductDTO productDTO) throws MyException;

    ProductDTO getProductByProduct_id(BigInteger product_id) throws MyException;
}
