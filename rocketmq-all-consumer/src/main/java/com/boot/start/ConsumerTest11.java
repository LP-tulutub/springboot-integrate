package com.boot.start;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.consumer.rebalance.AllocateMessageQueueAveragelyByCircle;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
import org.springframework.stereotype.Component;

import java.util.List;


@Slf4j
@Component
@RocketMQMessageListener(topic = "springboot-mq-test11", consumerGroup = "springboot-mq-consumer-11")
public class ConsumerTest11 implements RocketMQListener<Object>, RocketMQPushConsumerLifecycleListener {

    @Override
    public void onMessage(Object o) {
        // 不会使用的方法
    }

    @Override
    public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
        // 更换消费策略
        defaultMQPushConsumer.setAllocateMessageQueueStrategy(new AllocateMessageQueueAveragelyByCircle());
        // 获取消费信息，判断是否需要重新消费
        defaultMQPushConsumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                for (MessageExt messageExt : list) {
                    final String msg = new String(messageExt.getBody());
                    log.warn("ConsumerTest11 接受到的消息:" + msg + "|||重试次数:" + messageExt.getReconsumeTimes() + "|||consumeConcurrentlyContext:" + consumeConcurrentlyContext.getMessageQueue());
                    if (msg.equals("test11发送的内容2") && messageExt.getReconsumeTimes() < 2)
                        return ConsumeConcurrentlyStatus.RECONSUME_LATER; // 重新消费，默认最大次数为 16
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS; // 完成消费
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS; // 完成消费
            }
        });

    }
}
