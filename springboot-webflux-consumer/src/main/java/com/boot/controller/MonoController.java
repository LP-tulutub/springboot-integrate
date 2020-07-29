package com.boot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Controller
@RequestMapping("/mono")
@Slf4j
public class MonoController {

    @GetMapping("/test01")
    @ResponseBody
    public Mono<Object> test01(){
        return Mono.create(monoSink -> {
            log.warn("创建 Mono");
            monoSink.success("mono-test01");
        }).doOnSubscribe(subscription -> {
            log.warn("{}", subscription);
        }).doOnNext(o -> {
            log.warn(o.toString());
        }); // 一个消息操作，返回是否成功
    }

    @GetMapping("/test02")
    @ResponseBody
    public Mono<Object> test02(){
        //Mono.justOrEmpty("mono-test02");
        return Mono.just("mono-test02"); // 返回一个 Object
    }

    @GetMapping("/test03")
    @ResponseBody
    public Mono<Object> test03(){
        // do somethings
        return Mono.empty(); // 返回结束(用户空白页)
    }

    @GetMapping("/test04")
    @ResponseBody
    public Mono<Object> test04(){
        return Mono.never(); // 返回空(用户一直处于等待状态)
    }

    @GetMapping("/test05")
    @ResponseBody
    public Mono<Object> test05(){
        return Mono.error(new Throwable("test05 Mono.error")); // 返回错误
    }

    @GetMapping("/test06")
    @ResponseBody
    public Mono<Object> test06(){
        // fromCallable()、fromCompletionStage()、fromFuture()、fromRunnable()、fromSupplier()
        // 分别从Callable、CompletionStage、CompletableFuture、Runnable、Supplier 中创建 Mono
        return Mono.fromCallable(() -> "mono-test06: " + Thread.currentThread().getName()); // 使用 Callable 返回
    }

    @GetMapping("/test07")
    @ResponseBody
    public Mono<Long> test07(){
        // 通常结合 donext 来做延迟任务
        return Mono.delay(Duration.ofSeconds(3)); // 延迟返回
    }

}
