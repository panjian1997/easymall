package cn.tedu;

import cn.tedu.config.MyConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ConfiguMain {
    public static void main(String[] args) {
        //spring提供的注解配置类加载方式
        //ClasspathXml
        AnnotationConfigApplicationContext
                context=new AnnotationConfigApplicationContext(MyConfig.class);
    }
}
