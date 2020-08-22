package cn.tedu.es;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.pojo.Product;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequestBuilder;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.delete.DeleteRequestBuilder;
import org.elasticsearch.action.get.GetRequestBuilder;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ESTest {
    /*
    准备一个连接对象,能够连接es的集群
     */
    private TransportClient client;
    //@Before每次调用test之前对client都赋值
    @Before
    public void init() throws Exception {
        client=new PreBuiltTransportClient(Settings.EMPTY);
        //准备连接节点ip host TCP/IP 底层封装socket
        TransportAddress address1=new
                InetSocketTransportAddress(
                InetAddress.getByName("10.9.39.13"),9300);
        TransportAddress address2=new
                InetSocketTransportAddress(
                InetAddress.getByName("10.9.104.184"),9300);
        TransportAddress address3=new
                InetSocketTransportAddress(
                InetAddress.getByName("10.9.100.26"),9300);
        //将address 1 2 3 交给client使用
        client.addTransportAddresses(address1,address2,address3);
    }
    //索引的管理
    @Test
    public void indexManage(){
        //client连接es集群,操作索引需要先从对象获取一个操作索引的管理对象
        IndicesAdminClient indices = client.admin().indices();
        //创建索引 等同于curl -XPUT http://10.9.39.13:9200/easymall 响应{"ack","shards-ac"}
        CreateIndexRequestBuilder request1 = indices.prepareCreate("easymall");
        CreateIndexResponse response1 = request1.get();
        //从response1里解析很多响应内容 包括响应体json解析
        System.out.println("创建成功:"+response1.isAcknowledged());
        System.out.println("分片创建成功:"+response1.isShardsAcked());
        //在代码客户端中与索引管理有关的所有api方法都是IndeicesAdminClient完成
        //其他方法--判断索引存在
        IndicesExistsRequestBuilder request2 = indices.prepareExists("easymall");
        IndicesExistsResponse response2 = request2.get();
        System.out.println("索引存在:"+response2.isExists());
        //删除索引
        DeleteIndexRequestBuilder request3 = indices.prepareDelete("easymall");
        DeleteIndexResponse response3 = request3.get();
        System.out.println("删除是否成功:"+response3.isAcknowledged());
    }
    //文档管理,新增文旦,删除文档,获取文档
    //文档数据怎么想es中写入存储
    @Test
    public void documentManage() throws JsonProcessingException {
        //新增文档数据
        Product product =new Product();
        product.setProductId("1");
        product.setProductNum(50);
        product.setProductImgurl("image");
        product.setProductDescription("好看");
        product.setProductCategory("手机");
        product.setProductPrice(55.5);
        product.setProductName("华为");
        //新增文档对象
        //将对象数据转化成传递给es的json字符串
        ObjectMapper mapper=new ObjectMapper();
        String json = mapper.writeValueAsString(product);
        //从请求对象中,添加这个数据源文档对象数据
        IndexRequestBuilder request1 = client
                .prepareIndex("easymall", "product",
                        product.getProductId());
        request1.setSource(json);//随着请求 发送想es一条document数据
        //curl -XPUT http://localhost:9200/index01/article/1 -d 'json'
        IndexResponse response1 = request1.get();
        //_index _type _id _version 等等
        System.out.println("索引:"+response1.getIndex());
        System.out.println("类型:"+response1.getType());
        System.out.println("id:"+response1.getId());
        System.out.println("版本:"+response1.getVersion());
        //获取文档数据
        GetRequestBuilder request2 = client.prepareGet
                ("easymall", "product", "1");
        GetResponse response2 = request2.get();
        System.out.println("文档数据:"+response2.getSourceAsString());
        //删除文档发送delete请求
        DeleteRequestBuilder request3 = client.prepareDelete
                ("easymall", "product", "1");
        //request3.get();
    }
    //搜索功能的使用
    @Test
    public void search(){
        //构造查询的方法,使用查询参数
        /*QueryBuilders.matchAllQuery();
        QueryBuilders.termQuery("haha","haha");
        QueryBuilders.fuzzyQuery("title","tramp");
        */
        //TermQueryBuilder query =
                //QueryBuilders.termQuery("productName", "华");
        MatchQueryBuilder query = QueryBuilders.matchQuery("productName", "华为手机真不错");
        //执行搜索
        //request对象获取
        SearchRequestBuilder request =
                client.prepareSearch("easymall");
        //request添加各种参数page rows分页数据条件 查询query
        request.setQuery(query).setFrom(0).setSize(5);
        SearchResponse response = request.get();
        //解析response中的查询结果{hits:{"hits":[{}{}{}{}{}]}}
        SearchHits allHits = response.getHits();
        SearchHit[] hits = allHits.getHits();
        for(SearchHit hit:hits){
            //从每个元素中获取source的json字符串
            System.out.println("获取source的json:"+hit.getSourceAsString());
        }
    }
}
