package com.boot.tart;



import com.boot.AppRocketMQAllProducer;
import com.boot.start.pojo.MyUser;
import com.boot.start.controller.ProducerAllController;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.UUID;


@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AppRocketMQAllProducer.class})
public class ProducerAll {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    @Autowired
    private ProducerAllController producerAllService;

    /**
     * 单向发送
     */
    @Test
    public void test01(){
        this.rocketMQTemplate.convertAndSend("springboot-mq-test01","test01发送的内容");
    }

    /**
     * 同步发送
     */
    @Test
    public void test02(){
        SendResult result = this.rocketMQTemplate.syncSend("springboot-mq-test02", "test02发送的内容");
        log.warn("同步发送 test02: " + result.getMessageQueue() + "||||||||Class:" + result);
    }

    /**
     * 异步发送
     */
    @Test
    public void test03(){
        this.rocketMQTemplate.asyncSend("springboot-mq-test03", "test03发送的内容", new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.warn("异步发送 onSuccess: " + sendResult.getMessageQueue() + "||||||||Class:" + sendResult);
            }

            @Override
            public void onException(Throwable throwable) {
                log.warn("异步发送 onException: " + throwable.getMessage());
            }
        });
    }

    /**
     * 批量发送
     */
    @Test
    public void test04(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("myMessage", "内容");
        map.put("test04", "test04发送的内容");
        this.rocketMQTemplate.convertAndSend("springboot-mq-test04", map);
    }

    /**
     * 延时发送
     */
    @Test
    public void test05(){
        Message<String> message = MessageBuilder.withPayload("test05发送的内容").build();
        SendResult result = this.rocketMQTemplate.syncSend("springboot-mq-test05", message, 10000, 3);
        log.warn("延时发送 test05: " + result.getMessageQueue() + "||||||||Class:" + result);
    }

    /**
     * tag 过滤发送
     */
    @Test
    public void test06(){
        this.rocketMQTemplate.convertAndSend("springboot-mq-test06:tag06", "test06发送的内容");
        this.rocketMQTemplate.convertAndSend("springboot-mq-test06:tag062", "test06发送的内容2");
    }

    /**
     * sql 过滤发送
     */
    @Test
    public void test07(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("si", "5");

        this.rocketMQTemplate.convertAndSend("springboot-mq-test07", "test07发送的内容", map);
        this.rocketMQTemplate.convertAndSend("springboot-mq-test07", "test07发送的内容2",map);
    }

    /**
     * 顺序发送
     */
    @Test
    public void test08(){
        // 顺序发送
        // 相同的hashKey，就相当于相同的MessageQueue
        // 相同的MessageQueue下消息是顺序的
        this.rocketMQTemplate.syncSendOrderly("springboot-mq-test08", "test08发送的内容1", "1");
        this.rocketMQTemplate.syncSendOrderly("springboot-mq-test08", "test08发送的内容2", "1");
        this.rocketMQTemplate.syncSendOrderly("springboot-mq-test08", "test08发送的内容3", "1");
        this.rocketMQTemplate.syncSendOrderly("springboot-mq-test08", "test08发送的内容4", "4");
        this.rocketMQTemplate.syncSendOrderly("springboot-mq-test08", "test08发送的内容5", "4");
    }

    /**
     * 事务安全
     */
    @Test
    public void test09() throws InterruptedException {
        // 开启服务才会回查
        Message<MyUser> message = MessageBuilder.withPayload(new MyUser(1, "张三", 22, "飞翔")).build();
        Message<MyUser> message2 = MessageBuilder.withPayload(new MyUser(2, "李四", 23, "起飞")).build();
        Message<MyUser> message3 = MessageBuilder.withPayload(new MyUser(3, "王五", 30, "飞天")).build();
        this.rocketMQTemplate.sendMessageInTransaction("springboot-mq-test09", message, 1);
        this.rocketMQTemplate.sendMessageInTransaction("springboot-mq-test09", message2, 2);
        this.rocketMQTemplate.sendMessageInTransaction("springboot-mq-test09", message3, 3);

    }

    /**
     * 负载均衡
     */
    @Test
    public void test10(){
        this.rocketMQTemplate.syncSend("springboot-mq-test10", "test10发送的内容1");
        this.rocketMQTemplate.syncSend("springboot-mq-test10", "test10发送的内容2");
        this.rocketMQTemplate.syncSend("springboot-mq-test10", "test10发送的内容3");
        this.rocketMQTemplate.syncSend("springboot-mq-test10", "test10发送的内容4");
        this.rocketMQTemplate.syncSend("springboot-mq-test10", "test10发送的内容5");
        this.rocketMQTemplate.syncSend("springboot-mq-test10", "test10发送的内容6");
        this.rocketMQTemplate.syncSend("springboot-mq-test10", "test10发送的内容7");
        this.rocketMQTemplate.syncSend("springboot-mq-test10", "test10发送的内容8");
        this.rocketMQTemplate.syncSend("springboot-mq-test10", "test10发送的内容9");
        this.rocketMQTemplate.syncSend("springboot-mq-test10", "test10发送的内容10");
    }

    /**
     * 消息重试
     */
    @Test
    public void test11(){
        this.rocketMQTemplate.syncSend("springboot-mq-test11", "test11发送的内容1");
        this.rocketMQTemplate.syncSend("springboot-mq-test11", "test11发送的内容2");
    }

    /**
     * 消息冥等性(消息不能重复消费)
     */
    @Test
    public void test12(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("myUserID", "LP");
        map.put("myVersion", UUID.randomUUID() + "" + System.currentTimeMillis());

        map.put("myTable", "order");
        this.rocketMQTemplate.convertAndSend("springboot-mq-test12", "test12发送order订单信息", map);

        map.put("myTable", "information");
        this.rocketMQTemplate.convertAndSend("springboot-mq-test12", "test12发送information的内容", map);

    }

}
