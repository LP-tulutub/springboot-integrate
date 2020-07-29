package com.boot.feign.fallback;

import com.boot.feign.SentinelFeign;
import org.springframework.stereotype.Component;

@Component
public class SentinelFeignFallback implements SentinelFeign {
    @Override
    public String connect01() {
        return "SentinelFeignFallback-ERROR: connect01";
    }

    @Override
    public String connect02() {
        return "SentinelFeignFallback-ERROR: connect02";
    }

    @Override
    public String connect03() {
        return "SentinelFeignFallback-ERROR: connect03";
    }
}
