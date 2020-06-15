package com.boot.start;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.SelectorType;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RocketMQMessageListener(topic = "springboot-mq-test07", consumerGroup = "springboot-mq-consumer-07", accessKey = "si", selectorType = SelectorType.SQL92, selectorExpression = "si = 5")
public class ConsumerTest07 implements RocketMQListener<Object> {
    @Override
    public void onMessage(Object o) {
        log.warn("ConsumerTest07 接收到消息:" + o);
    }

}
