package cn.tedu.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

//feign的代理,需要使用注解
@FeignClient(name="service-hi")
//当前这个接口的底层实现类必须都去访问service-hi的服务
//作为被调用的微服务可能存在多个功能url不同,如何定义
public interface HelloService {
    //通过抽象方法添加springmvc注解,注解使用要规范
    @RequestMapping(value="/client/hello",method = RequestMethod.GET)
    //restTemplate.getForObject("http://service-hi/client/hello",String.class)
    String sayHi(@RequestParam(value = "name") String name);

}
