package com.boot.start;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
import org.springframework.stereotype.Component;

import java.util.List;


@Slf4j
@Component
@RocketMQMessageListener(topic = "springboot-mq-test09", consumerGroup = "springboot-mq-consumer-09")
public class ConsumerTest09 implements RocketMQListener<Object>, RocketMQPushConsumerLifecycleListener {

    @Override
    public void onMessage(Object o) {
        log.warn("ConsumerTest09 接收到消息:" + o);
    }

    @Override
    public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {

        defaultMQPushConsumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                for (MessageExt messageExt : list) {
                    log.warn("重试次数:" + messageExt.getReconsumeTimes());
                    log.warn("接受到的消息:" + new String(messageExt.getBody()));
                    log.warn("consumeConcurrentlyContext:" + consumeConcurrentlyContext.getMessageQueue());
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

    }
}
