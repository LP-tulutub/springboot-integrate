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
import java.util.concurrent.ConcurrentHashMap;


@Slf4j
@Component
@RocketMQMessageListener(topic = "springboot-mq-test12", consumerGroup = "springboot-mq-consumer-12")
public class ConsumerTest12 implements RocketMQListener<Object>, RocketMQPushConsumerLifecycleListener {

    // 模拟数据库
    private static ConcurrentHashMap<String, String> localSQL = new ConcurrentHashMap<>();

    @Override
    public void onMessage(Object o) {
        // 不会使用的方法
    }

    /**
     * 输出结果：
     *  ConsumerTest12 消费完成: test12发送order订单信息|||重试次数:0|||consumeConcurrentlyContext:MessageQueue [topic=springboot-mq-test12, brokerName=broker-b, queueId=1]
     *  ConsumerTest12 消费完成: test12发送information的内容|||重试次数:0|||consumeConcurrentlyContext:MessageQueue [topic=springboot-mq-test12, brokerName=broker-b, queueId=2]
     *  ConsumerTest12 不消费消息: test12发送information的内容
     * @param defaultMQPushConsumer
     */
    @Override
    public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {

        defaultMQPushConsumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                for (MessageExt messageExt : list) {
                    final String myVersion = messageExt.getProperty("myVersion");
                    final String localID = messageExt.getProperty("myUserID") + messageExt.getProperty("myTable");
                    final String msg = new String(messageExt.getBody());
                    // 模拟存储数据库操作
                    String localO = null;
                    if (localSQL.containsKey(localID))
                        localO = localSQL.get(localID);
                    if (localO != null && localO.equals(myVersion)){
                        log.warn("ConsumerTest12 不消费消息: " + msg);
                        continue;
                    }
                    localSQL.put(localID, myVersion);
                    log.warn("ConsumerTest12 消费完成: " + msg + "|||重试次数:" + messageExt.getReconsumeTimes() + "|||consumeConcurrentlyContext:" + consumeConcurrentlyContext.getMessageQueue());
                    // 模拟消息可能重复消费的情况
                    if (msg.equals("test12发送information的内容") && messageExt.getReconsumeTimes() == 0)
                        return ConsumeConcurrentlyStatus.RECONSUME_LATER; // 重新消费，默认最大次数为 16
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS; // 完成消费
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS; // 完成消费
            }
        });

    }
}
