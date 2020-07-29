package com.boot.controller;

import com.boot.service.WebFluxService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Arrays;

@Controller
@RequestMapping("/flux")
@Slf4j
public class FluxController {

    @Autowired
    private WebFluxService webFluxService;

    @GetMapping("/test01")
    @ResponseBody
    public Flux<Object> test01(){
        return Flux.create(objectFluxSink -> {
            log.warn("objectFluxSink: " + objectFluxSink.toString());
            objectFluxSink.next("flux-test01-obj01");
            objectFluxSink.next("flux-test01-obj02");
            objectFluxSink.next("flux-test01-obj03");
            objectFluxSink.complete();
        }).doOnSubscribe(subscription -> {
            log.warn("subscription: " + subscription.toString());
        }).doOnNext(o -> {
            log.warn("o: " + o.toString());
        }); // 多个消息操作，返回是否成功
    }

    @GetMapping("/test02")
    @ResponseBody
    public Flux<Object> test02(){
        // Mono.just("flux-test02-obj01", "flux-test02-obj02", "flux-test02-obj03");
        return Flux.just("flux-test02"); // 返回多个 Object
    }

    @GetMapping("/test03")
    @ResponseBody
    public Flux<Object> test03(){
        // do somethings
        return Flux.empty(); // 返回结束(用户空白页)
    }

    @GetMapping("/test04")
    @ResponseBody
    public Flux<Object> test04(){
        return Flux.never(); // 返回空(用户一直处于等待状态)
    }

    @GetMapping("/test05")
    @ResponseBody
    public Flux<Object> test05(){
        return Flux.error(new Throwable("test05 Flux.error")); // 返回错误
    }

    @GetMapping("/test06")
    @ResponseBody
    public Flux<Object> test06(){
        // fromArray()，fromIterable()、fromStream()
        // 可以从一个数组、Iterable 对象或 Stream 对象中创建 Flux 对象
        return Flux.fromStream(Arrays.stream(new Object[]{"flux-test06-obj01"})); // 使用 Stream 返回
    }

    @GetMapping("/test07")
    @ResponseBody
    public Flux<Object> test07(){
        // 类似消息中间件的一些功能，失败重试次数 2
        return Flux.concatDelayError(webFluxService::publisher, 2); // 返回空
    }

    @GetMapping("/test08")
    @ResponseBody
    public Flux<Object> test08(){
        // 输出 1-100，每组 20 个，打印到控制台
        Flux.range(1, 100).buffer(20).subscribe(System.out::println);
        // 延迟 50 毫秒，打印 101 个数，打印 2 组
        Flux.interval(Duration.ofMillis(50)).buffer(101).take(2).subscribe(System.out::println);
        // 输出 1-10，奇数坐标 0，偶数坐标 1，打印到控制台
        Flux.range(1, 10).bufferUntil(i -> i % 2 == 0).subscribe(System.out::println);
        // 需要 100 / 20 个 UnicastProcessor
        Flux.range(1, 100).window(20).subscribe(System.out::println);
        // 加入新的元素
        Flux.just("a", "b").zipWith(Flux.just("c", "d")).subscribe(System.out::println);
        // 加入新的元素 + 自定义打印格式
        Flux.just("a", "b").zipWith(Flux.just("c", "d"), (s1, s2) -> String.format("%s-%s", s1, s2)).subscribe(System.out::println);
        // 1-1000 打印前 10 个，从小到大
        Flux.range(1, 1000).take(10).subscribe(System.out::println);
        // 1-1000 打印后 10 个，从小到大
        Flux.range(1, 1000).takeLast(10).subscribe(System.out::println);
        // 1-1000 打印小于 10 的数
        Flux.range(1, 1000).takeWhile(i -> i < 10).subscribe(System.out::println);
        // 1-1000 打印小于等于 10 的数
        Flux.range(1, 1000).takeUntil(i -> i == 10).subscribe(System.out::println);
        // 打印 0-4
        Flux.interval(Duration.ZERO, Duration.ofMillis(100)).take(5).subscribe(System.out::println);
        // 2 个 Flux 都会打印，有顺序
        Flux.merge(Flux.interval(Duration.ZERO, Duration.ofMillis(100)).take(5), Flux.interval(Duration.ofMillis(50), Duration.ofMillis(100))).subscribe(System.out::println);
        Flux.merge(Flux.interval(Duration.ZERO, Duration.ofMillis(100)).take(5), Flux.interval(Duration.ofMillis(50), Duration.ofMillis(100)).take(5)).subscribe(System.out::println);
        // 作用效果与上面 2 条类似
        Flux.just(5, 10).flatMap(x -> Flux.interval(Duration.ofMillis(x * 10), Duration.ofMillis(100)).take(x)).subscribe(System.out::println);
        // 通过 checkpoint 操作符来对特定的流处理链来启用调试模式，当出现错误时，检查点名称会出现在异常堆栈信息中
        //Flux.just(1, 0).map(x -> 1 / x).checkpoint("test").subscribe(System.out::println);

        return Flux.just("SUCCESS");
    }

}
