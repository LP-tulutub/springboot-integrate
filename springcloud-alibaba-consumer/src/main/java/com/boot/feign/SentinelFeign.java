package com.boot.feign;

import com.boot.feign.fallback.SentinelFeignFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "springcloud-alibaba-producer", fallback = SentinelFeignFallback.class)
public interface SentinelFeign {
    @GetMapping("/producer/connect01")
    public String connect01();

    @GetMapping("/producer/connect02")
    public String connect02();

    @GetMapping("/producer/connect03")
    public String connect03();

}
