package cn.haha;

import org.springframework.stereotype.Component;

@Component
public class Bean2 {
    public Bean2(){
        System.out.println("bean2被加载创建了");
    }
}
