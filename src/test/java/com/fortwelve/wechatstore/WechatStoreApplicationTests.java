package com.fortwelve.wechatstore;

import com.fortwelve.wechatstore.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WechatStoreApplicationTests {

   /* @Autowired
    DataSource dataSource;
    @Autowired
    CustomerMapper customerMapper;
    @Autowired
    CategoryMapper categoryMapper;
    @Autowired
    PictureMapper pictureMapper;
    @Autowired
    PropertyKeyMapper propertyKeyMapper;
    @Autowired
    PropertyValueMapper propertyValueMapper;
    @Autowired
    ProductMapper productMapper;
    @Autowired
    SkuMapper skuMapper;
    @Autowired
    SkuService skuService;
    @Autowired
    ProductService productService;



    @Test
    void contextLoads() throws SQLException {
        Connection cn = dataSource.getConnection();
        System.out.println(cn);
        cn.close();
    }

    @Test
    void testCategoryMapper(){
        System.out.println(customerMapper.getCustomerById(new java.math.BigInteger("1")));
        System.out.println(customerMapper.getAllCustomer());

        System.out.println(categoryMapper.getCategoryById(2));

        Category category = new Category();
        category.setCategory_name("'\"分类名\"'");
//        System.out.println(categoryMapper.addCategory(category));
//        System.out.println(categoryMapper.getCategoryById(category.getCategory_id()));
//
//        System.out.println(categoryMapper.getAllCategory());
//        Category category2 = new Category();
//        category2.setCategory_id(8);
//        category2.setCategory_name("修改后的分类名");
//        System.out.println(categoryMapper.updateCategory(category2));
//        System.out.println(categoryMapper.deleteCategory(7));
    }

    @Test
    void testCustomerMapper(){
        System.out.println("添加志杰");
        Customer customer1 = new Customer();
        customer1.setCustomer_name("陈志杰");
        customerMapper.addCustomer(customer1);
        System.out.println(customer1.getCustomer_id());
//        System.out.println("获取梓鹏");
//
//        Customer customer2=customerMapper.getCustomerById(new java.math.BigInteger("1"));
//        System.out.println(customer2);
//        customer2.setCustomer_name("邓梓鹏");
//
//        System.out.println("修改梓鹏为邓梓鹏");
//
//        System.out.println(customerMapper.updateCustomer(customer2));
//
//        System.out.println("添加错误名字");
//        Customer customer3 = new Customer(new java.math.BigInteger("3"),"dingyunyu");
//        customerMapper.addCustomer(customer3);
//        System.out.println(customerMapper.getAllCustomer());
//        System.out.println("插入后的主键");
//        System.out.println(customer3.getCustomer_id());
//        System.out.println("删除错误名字");
//        System.out.println(customerMapper.deleteCustomerById(new BigInteger("3")));

    }

    @Test
    void testPictureMapper(){
        Picture picture1 = new Picture();
        picture1.setUrl("picture1.jpg");
        pictureMapper.addPicture(picture1);

        System.out.println("返回的picture_id："+picture1.getPicture_id());
        Picture picture2 = new Picture(new java.math.BigInteger("1"),"url3.jpg");
        pictureMapper.addPicture(picture2);
        System.out.println("返回的picture_id："+picture2.getPicture_id());
        System.out.println(pictureMapper.getAllPicture());
        System.out.println(pictureMapper.getPictureById(picture2.getPicture_id()));
        picture2.setUrl("url2.jpg");
        pictureMapper.updatePicture(picture2);
        System.out.println(pictureMapper.deletePictureById(picture1.getPicture_id()));
        System.out.println(pictureMapper.getAllPicture());
    }
    @Test
    void testKeyAndValue(){
        PropertyKey propertyKey = new PropertyKey();
        PropertyValue propertyValue= new PropertyValue();

//        propertyKey.setKey_name("尺寸");
//        propertyKey.setPicture(false);
//        propertyKeyMapper.addPropertyKey(propertyKey);
//        propertyKey.setKey_name("颜色");
//        propertyKey.setPicture(false);
//        propertyKeyMapper.addPropertyKey(propertyKey);
//
//        propertyValue.setValue_name("M");
//        propertyValue.setPicture_id(null);
//        propertyValueMapper.addPropertyValue(propertyValue);
//        propertyValue.setValue_name("L");
//        propertyValueMapper.addPropertyValue(propertyValue);
//        propertyValue.setValue_name("XL");
//        propertyValueMapper.addPropertyValue(propertyValue);
//
//        propertyValue.setValue_name("红色");
//        propertyValueMapper.addPropertyValue(propertyValue);
//        propertyValue.setValue_name("蓝色");
//        propertyValueMapper.addPropertyValue(propertyValue);
//        propertyValue.setValue_name("黑色");
//        propertyValueMapper.addPropertyValue(propertyValue);
//        propertyKeyMapper.deletePropertyKeyById(new java.math.BigInteger("3"));
//        propertyKeyMapper.deletePropertyKeyById(new java.math.BigInteger("4"));
//        propertyKeyMapper.deletePropertyKeyById(new java.math.BigInteger("5"));
//        propertyKeyMapper.deletePropertyKeyById(new java.math.BigInteger("6"));
        System.out.println(propertyKey = propertyKeyMapper.getPropertyKeyById(new BigInteger("2")));
        System.out.println(propertyValue = propertyValueMapper.getPropertyValueById(new BigInteger("4")));
//
//        System.out.println(propertyValueMapper.deletePropertyValueById(new BigInteger("5")));
//        System.out.println(propertyKeyMapper.getAllPropertyKey());
//        System.out.println(propertyValueMapper.getAllPropertyValue());
        propertyKey.setKey_name("修改过的key");
        propertyKey.setPicture(true);
        propertyValue.setValue_name("修改过的value");
        propertyValue.setPicture_id(new java.math.BigInteger("2"));
        propertyKeyMapper.updatePropertyKey(propertyKey);
        propertyValueMapper.updatePropertyValue(propertyValue);
    }
    @Test
    void testProduct(){
//        Timestamp timestamp = Timestamp.valueOf("2020-11-30 17:57:33");
//        System.out.println(timestamp);
//        Product product = new Product(null,"日系男士条纹休闲加绒加厚衬衫全棉口袋磨毛衬衣纯色保暖上衣外套",new java.math.BigDecimal("98.00"),1,Timestamp.valueOf("2020-11-30 17:57:33"),null,"商品描述1",BigInteger.valueOf(1l));
//        productMapper.addProduct(product);
//        System.out.println("插入后的id:"+product.getProduct_id());
//        product.setProduct_name("秋季港风ins格子衬衫男士长袖韩版潮流帅气外套休闲情侣衬衣修身");
//        productMapper.addProduct(product);
//        productMapper.addProduct(product);
//        productMapper.addProduct(product);
//        productMapper.addProduct(product);
//        productMapper.addProduct(product);
//        productMapper.addProduct(product);
//        System.out.println("插入后的id:"+product.getProduct_id());

//        System.out.println(productMapper.getAllProduct());
//        System.out.println(productMapper.getProductById(BigInteger.valueOf(2l)));
//        System.out.println(productMapper.getProductPage(2, 10));

//        System.out.println(productMapper.deleteProductById(BigInteger.valueOf(4l)));
        List<String> keywords = new ArrayList<>();
        keywords.add("男");
        keywords.add("条纹");
        List<Product> products = productService.searchProductPage(keywords, 0, 5);
        System.out.println(products.size());
        System.out.println(products);
//        System.out.println(productMapper.searchProductPage(keywords, 0, 5));
//        System.out.println(productMapper.searchProductPage2("男"));
//        Product product = productMapper.getProductById(BigInteger.valueOf(5l));
//        product.setProduct_name("修改过的");
//        product.setPrice(BigDecimal.valueOf(59.9));
//        System.out.println(productMapper.updateProduct(product));
    }

    @Test
    void testSku(){
        Sku sku = new Sku();
//        sku.setProduct_id(BigInteger.valueOf(3l));
//        sku.setPicture_id(BigInteger.valueOf(2l));
//        sku.setProperties("1:2_2:4");
//        sku.setSku_price(BigDecimal.valueOf(59.9));
//        skuMapper.addSku(sku);
//        System.out.println("skuid:"+sku.getSku_id());
//        System.out.println(skuMapper.getAllSku());
//        sku = skuMapper.getSkuById(BigInteger.valueOf(5));
//        System.out.println(sku);
//        sku.setSku_price(BigDecimal.valueOf(9.9));
//        skuMapper.updateSku(sku);
//        System.out.println(skuMapper.deleteSkuById(BigInteger.valueOf(4)));
//        System.out.println(skuService.getSkuByProductId(BigInteger.valueOf(2)));
        Map<String,String> map=new HashMap<>();

        String str1="123";
        String str2=new String("123");
        System.out.println(str1==str2+":相同判断");


        map.put(str1,"a");
        map.put("2","b");
        map.put(str2,"111");
        System.out.println(map);

    }

    @Test
    void testJackson(){
        ObjectMapper mapper = new ObjectMapper();
        Map<String,Object> map = new HashMap<>();
        Map<String,Object> msgbody = new HashMap<>();
        Map<String,Object> meta = new HashMap<>();
        List<Object> msg = new ArrayList<>();

        System.out.println("接受");
        msgbody.put("image_src","https://api-hmugo-web.itheima.net/pyg/banner1.png");
        msgbody.put("open_type","navigate");
        msgbody.put("goods_id",129);
        msgbody.put("navigator_url","/pages/goods_detail/index?goods_id=129");
        msg.add(msgbody);

        meta.put("msg","获取成功");
        meta.put("status",200);

        map.put("message",msg);
        map.put("meta",meta);
        try {
            String str=mapper.writeValueAsString(map);
            System.out.println(str);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }*/
   @Autowired
   CategoryService categoryService;
   @Test
   public void Test(){
       System.out.println(categoryService.getAllCategory());
   }
}
