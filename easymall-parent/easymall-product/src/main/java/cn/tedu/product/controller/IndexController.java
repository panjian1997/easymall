/*
package cn.tedu.product.controller;

import cn.tedu.product.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.pojo.Product;
import com.jt.common.vo.EasyUIResult;
import com.jt.common.vo.SysResult;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.client.transport.TransportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class IndexController {
    //注入一个transportClient
    @Autowired
    private TransportClient client;
    @Autowired
    private ProductService productService;


    private static final ObjectMapper mapper=new ObjectMapper();
    @RequestMapping("create/{indexName}/{typeName}")
    public SysResult createIndex(@PathVariable String indexName,
                                 @PathVariable String typeName){
        //client判断索引是否存在,不存在则创建,存在则直接使用
            //获取index的管理对象admin
        IndicesAdminClient admin = client.admin().indices();
            //判断存在
        IndicesExistsResponse indexResponse = admin.prepareExists(indexName).get();
        if(!indexResponse.isExists()){
            //创建一个新索引
            admin.prepareCreate(indexName).get();
        }
        //将数据从数据库读取出来List<Product> product
        EasyUIResult easyUIResult = productService.queryByPage(1, 100);//如果数据量非常大,成批读取写入文档
        //拿到商品对象list
        List<Product> pList=(List<Product>)easyUIResult.getRows();
        //挨个的将product对象放到json中传递给es生成文档数据
        try{
            for(Product p:pList){
                String json=mapper.writeValueAsString(p);
                //当做文档数据参数写到es中
                IndexRequestBuilder request =
                        client.prepareIndex(indexName,
                                typeName, p.getProductId());
                request.setSource(json).get();
            }
            return SysResult.ok();
        }catch (Exception e ){
            e.printStackTrace();
            return SysResult.build(201,"批量创建索引失败",null);
        }
    }
}
*/
