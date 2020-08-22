package cn.tedu;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HiController {
    //使用属性读取一个端口号
    @Value("${server.port}")
    private String port;
    //测试访问案例,携带参数name 返回hello world 端口号
    @RequestMapping(value="/client/hello",method=
            {RequestMethod.GET,RequestMethod.POST})
    public String sayHi(String name){
        return "helloworld eureka "+name+",i am from "+port;
    }
}
