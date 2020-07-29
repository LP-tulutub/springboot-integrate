package com.boot.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.boot.feign.SentinelFeign;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/sentinel")
@RefreshScope
public class SentinelController {

    @Resource
    private SentinelFeign sentinelFeign;

    @GetMapping("/test01")
    @SentinelResource(value = "sentinel-test01", blockHandler = "sentinelAllHandler")
    public Object test01(){
        return "test01";
    }

    @GetMapping("/test02")
    @SentinelResource(value = "sentinel-test02", fallback = "sentinelAllFallback")
    public Object test02(){
        int val = 10/0;
        return "test02";
    }

    /**
     * blockHandler + fallback
     * 访问 http://localhost:10022/sentinel/test03?id=1
     * @param id
     * @return
     */
    @GetMapping("/test03")
    @SentinelResource(value = "sentinel-test03", blockHandler = "sentinelTest03Handler", fallback = "sentinelTest03Fallback")
    public Object test03(@RequestParam String id){
        int val = 10/0;
        return "test03";
    }

    /**
     * feign + 消费者 sentinel
     * @return
     */
    @GetMapping("/test04")
    @SentinelResource(value = "sentinel-test04", blockHandler = "sentinelAllHandler")
    public Object test04(){
        return this.sentinelFeign.connect01();
    }

    /**
     * 测试 feign 的 fallback
     * @return
     */
    @GetMapping("/test05")
    @SentinelResource(value = "sentinel-test05")
    public Object test05(){
        return this.sentinelFeign.connect02();
    }

    /**
     * feign + 提供者 sentinel
     * @return
     */
    @GetMapping("/test06")
    @SentinelResource(value = "sentinel-test06")
    public Object test06(){
        return this.sentinelFeign.connect03();
    }

    public Object sentinelAllHandler(BlockException e){
        return "SentinelAllHandler: " + e.getCause() + ", " + e.getMessage();
    }

    public Object sentinelAllFallback(Throwable e){
        return "SentinelAllFallback: " + e.getCause() + ", " + e.getMessage();
    }

    public Object sentinelTest03Handler(@RequestParam String id, BlockException e){
        return "sentinelTest03Handler: " + e.getCause() + ", " + e.getMessage() + id;
    }

    public Object sentinelTest03Fallback(@RequestParam String id, Throwable e){
        return "sentinelTest03Fallback: " + e.getCause() + ", " + e.getMessage() + id;
    }
}
