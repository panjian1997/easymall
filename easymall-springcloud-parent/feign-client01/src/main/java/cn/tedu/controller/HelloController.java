package cn.tedu.controller;

import cn.tedu.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @Autowired
    private HelloService helloService;
    //访问localhost:8095/feign/hello?name=wanglaoshi
    //feign客户端实现8091 8092 8093 负载均衡访问
    @RequestMapping("feign/hello")
    public String sayHi(String name){
        return "FEIGN:"+helloService.sayHi(name);
    }
}
