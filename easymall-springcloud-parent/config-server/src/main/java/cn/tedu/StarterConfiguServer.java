package cn.tedu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
//开启configserver
@EnableConfigServer
@EnableEurekaClient
public class StarterConfiguServer {
    public static void main(String[] args) {
        SpringApplication.run(StarterConfiguServer.class,args);
    }
}
