package com.boot.start;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.SelectorType;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RocketMQMessageListener(topic = "springboot-mq-test06", consumerGroup = "springboot-mq-consumer-06", selectorType = SelectorType.TAG, selectorExpression = "tag06 || tag062")
public class ConsumerTest06 implements RocketMQListener<Object> {
    @Override
    public void onMessage(Object o) {
        log.warn("ConsumerTest06 接收到消息:" + o);
    }

}
