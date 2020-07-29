package com.boot.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/feign")
public class FeignController {

    @GetMapping("/producer01")
    public Flux<Object> producer01(){
        return Flux.just("feign-producer01");
    }

}
