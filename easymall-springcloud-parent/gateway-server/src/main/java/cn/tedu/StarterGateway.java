package cn.tedu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.EnableZuulServer;

@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy
//@EnableZuulServer(无法加载一部分过滤器)
public class StarterGateway {
    public static void main(String[] args) {
        SpringApplication.run(StarterGateway.class,args);
    }
}
