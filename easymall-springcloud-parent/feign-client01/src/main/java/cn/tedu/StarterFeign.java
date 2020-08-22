package cn.tedu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@SpringBootApplication
//开启feign的代理注解,实现负载均衡底层调用
@EnableFeignClients
@EnableEurekaClient
public class StarterFeign {
    public static void main(String[] args) {
        SpringApplication.run(StarterFeign.class,args);
    }
}
