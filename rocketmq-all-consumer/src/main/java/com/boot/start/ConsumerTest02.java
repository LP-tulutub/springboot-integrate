package com.boot.start;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RocketMQMessageListener(topic = "springboot-mq-test02", consumerGroup = "springboot-mq-consumer-02")
public class ConsumerTest02 implements RocketMQListener<Object> {
    @Override
    public void onMessage(Object o) {
        log.warn("ConsumerTest02 接收到消息:" + o);
    }

}
