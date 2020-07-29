package com.boot.controller;

import com.boot.service.WebFluxService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
@RequestMapping("/webflux")
@Slf4j
public class WebFluxController {

    @Autowired
    private WebFluxService webFluxService;

    /**
     * web 正常启动
     * @return
     */
    @GetMapping("/test01")
    @ResponseBody
    public String test01() {
        return "SUCCESS";
    }

    /**
     * Mono 返回 0-1 个元素
     * @return
     */
    @GetMapping("/test02")
    @ResponseBody
    public Mono<String> test02() {
        return Mono.just(this.webFluxService.success());
    }

    /**
     * Flux 返回 0-n 个元素
     * 返回效果是 1 个
     * @return
     */
    @GetMapping("/test03")
    @ResponseBody
    public Flux<String> test03() {
        return Flux.fromArray(this.webFluxService.array());
    }

    /**
     * Flux 返回 0-n 个元素
     * 返回效果是 n 个
     * @return
     */
    @GetMapping(value = "/test04", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseBody
    public Flux<String> test04() {
        return Flux.just(this.webFluxService.array());
    }

    /**
     * test05 与 test06 对比测试
     * test05 占用主线程
     * test06 不占用主线程
     * 用户体验都是相同的延迟
     * @return
     */
    @GetMapping("/test05")
    @ResponseBody
    public String test05() {
        log.warn("test05 开始");
        String result = this.webFluxService.delaySuccess();
        log.warn("test05 结束");
        return result;
    }

    @GetMapping("/test06")
    @ResponseBody
    public Mono<String> test06() {
        log.warn("test06 开始");
        Mono<String> result = Mono.fromSupplier(webFluxService::delaySuccess);
        log.warn("test06 结束");
        return result;
    }

    @GetMapping("/test07")
    @ResponseBody
    public String test07() {
        log.warn("test07 开始");
        Mono<String> result = Mono.fromSupplier(webFluxService::delaySuccess);
        log.warn("test07 结束");
        return result.block();
    }
}
