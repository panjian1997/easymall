package cn.tedu.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * hystrix熔断机制添加,需要在service,主要是restTemplate调用方法上
 * 添加服务降级逻辑
 */
@Service
public class HiService {
    //注入一个RestTemplate
    @Autowired
    private RestTemplate template;
    //error为sayHi做后方法,代码结构要和sayHi完全一致,返回值类型,参数值类型
    public String error(String name){
        //一旦sayHi方法调用微服务原因失败,重新调用error方法
        return "sorry,error happened";
    }
    //通过调用restTemolate对象的api方法，实现访问service-hi，负载均衡
    //服务降级注解
    @HystrixCommand(fallbackMethod = "error")//当这个sayHi的方法
    //调用别的微服务时出现故障,延迟,超时,断路器就会生效,打开,关闭,半开状态相互切换
    //一定存在一部分调用请求超时失败,可以退而求其次的访问服务降级的方法error
    public String sayHi(String name) {
        //调用代码方式访问后端微服务
        //restTemplate不是一个new出来的对象，是加了@LoadBalanced 具备ribbon负载均衡能力
        //可以通过访问service-hi的方式
        String url="http://service-hi/client/hello?name="+name;
        String body = template.getForObject(url, String.class);
        // hello world name i am from 8091/8092 轮询
        return body;
    }
}
