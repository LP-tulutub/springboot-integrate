package com.boot.start;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RocketMQMessageListener(topic = "springboot-mq-test08", consumerGroup = "springboot-mq-consumer-08", consumeMode = ConsumeMode.ORDERLY)
public class ConsumerTest08 implements RocketMQListener<Object> {

    @Override
    public void onMessage(Object o) {
        log.warn("ConsumerTest08 接收到消息:" + o);
    }
}
