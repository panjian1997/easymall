package cn.tedu.objectmapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.pojo.Product;
import org.junit.Test;

import java.io.IOException;

public class MapperTest {
    //jackson提供的与json有关的api
    //比如springmvc 返回user对象,响应体中 {"":"","":""}
    private ObjectMapper mapper=new ObjectMapper();
    //除了能实现对象转化成json字符串,还能将json字符串转化成对象
    @Test
    public void test() throws IOException {
        //准备一个对象
        Product p=new Product();
        p.setProductId("1");
        p.setProductName("海尔电视");
        p.setProductPrice(5000.0);
        p.setProductCategory("电视");
        p.setProductDescription("免费修理");
        p.setProductImgurl("img");
        p.setProductNum(50);
        //参数是object类型的一个对象
        String json = mapper.writeValueAsString(p);//输出---序列化,将对象序列化成字符串
        System.out.println(json);
        //String pJson="{\"productName\":\"大彩电\"}"
        //提供json,提供需要json转化成为的类的反射
        Product product = mapper.readValue(json, Product.class);
    }
}
