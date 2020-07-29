package com.boot.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/producer")
@RefreshScope
@Slf4j
public class SentinelController {

    @GetMapping("/connect01")
    @SentinelResource(value = "producer-connect01")
    public String connect01(){
        return "connect01";
    }

    @GetMapping("/connect02")
    @SentinelResource(value = "producer-connect02")
    public String connect02() throws InterruptedException {
        Thread.sleep(3000);
        return "connect02";
    }

    @GetMapping("/connect03")
    @SentinelResource(value = "producer-connect03", blockHandler = "connect03BlockHandler")
    public String connect03(){
        return "connect03";
    }

    public String connect03BlockHandler(BlockException e){
        return "connect03BlockHandler-ERROR: connect03";
    }
}
