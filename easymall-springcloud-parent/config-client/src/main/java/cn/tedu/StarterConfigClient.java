package cn.tedu;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class StarterConfigClient {
    public static void main(String[] args) {
        SpringApplication.run(StarterConfigClient.class,args);
    }
    //添加读取的属性
    @Value("${application}")
    private String application;
}
