package com.boot.start;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RocketMQMessageListener(topic = "springboot-mq", consumerGroup = "springboot-mq-consumer")
public class Consumer implements RocketMQListener<Object> {

    @Override
    public void onMessage(Object message) {
        log.warn("Receive message："+message);
    }
}
