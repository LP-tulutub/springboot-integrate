package com.boot.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Flux;

//@FeignClient("springboot-webflux-producer")
public interface ProducerFeign {
    @GetMapping("/feign/producer01")
    public Flux<Object> producer01();
}
