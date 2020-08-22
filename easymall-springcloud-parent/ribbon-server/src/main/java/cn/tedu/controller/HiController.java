package cn.tedu.controller;

import cn.tedu.service.HiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HiController {
    @Autowired
    private HiService hs;
    //访问ribbon/hello请求地址
    @RequestMapping("ribbon/hello")
    public String sayHi(String name){
        //假设用户访问ribbon工程，ribbon工程需要调用service-hi才能返回一个完整数据
        return "RIBBON:"+hs.sayHi(name);
        //在业务层中，决定如何使用restTemplate发起想service-hi的访问
    }
}
