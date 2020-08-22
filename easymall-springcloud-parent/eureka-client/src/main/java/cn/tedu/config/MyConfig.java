package cn.tedu.config;

import cn.haha.Bean1;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 3.x出现spring版本后，可以用类代替xml配置文件
 * springxml配置文件内容
 * 包扫描<context:component-scan basepacke="cn.haha"></context:component-scan>
 * <bean></bean>
 */
@Configuration//一旦加载容器时，可以加载这样的配置类，就相当于之前版本spring加载xml
@ComponentScan("cn.haha")
public class MyConfig {
    //配置bean注解实现bean对象创建
    @Bean
    public Bean1 initBean(){
        return new Bean1();
    }
}
/*
* <!--注解方式DI-->
    <!--配置包扫描-->
    <context:component-scan base-package="cn.haha"/>
    <!--配置数据源-->
    <bean id="initBean" class="cn.haha.Bean1">
    </bean>
*/