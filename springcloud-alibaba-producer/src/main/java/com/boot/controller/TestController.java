package com.boot.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope //支持 Nacos 的动态刷新功能
public class TestController {

    @Value("${my.info}")
    private String myInfo;

    @GetMapping("/test01")
    public String test01(){

        return this.myInfo;
    }

}
