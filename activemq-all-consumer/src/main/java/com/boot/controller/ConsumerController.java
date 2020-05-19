package com.boot.controller;

import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.*;
import java.util.Date;
import java.util.Map;

@RestController
public class ConsumerController {

    @JmsListener(destination = "${mymq}")
    public void receiveMessage(Map map) {
        System.out.println(new Date().toString()+" --> --> mymq接收Map：" + map.get("map"));
    }

    /**
     * Queue 测试
     * @param message
     * @throws JMSException
     */
    @JmsListener(destination = "test02")
    public void receiveTest02(Message message) throws JMSException {
        System.out.println(new Date().toString()+" --> --> test02接收消息：" + message.getBooleanProperty("test"));
    }

    /**
     * Queue 接收修改头的消息
     * @param message
     * @throws Exception
     */
    @JmsListener(destination = "test03")
    public void receiveTest03(ActiveMQTextMessage message) throws Exception {
        System.out.println(new Date().toString()+" --> --> test03接收消息：" + message);
    }

    /**
     * Topic 接收修改头的消息
     * @param message
     * @throws Exception
     */
    @JmsListener(destination = "test04", containerFactory = "topicListenerFactory")
    public void receiveTest04(ActiveMQTextMessage message) throws Exception {
        System.out.println(new Date().toString()+" --> --> test04接收消息：" + message);
    }

    /**
     * Topic 持久化订阅
     * @param message
     * @throws Exception
     */
    @JmsListener(destination = "test04", containerFactory = "topicListenerSubFactory", subscription = "test04b")
    public void receiveTest04b(ActiveMQTextMessage message) throws Exception {
        System.out.println(new Date().toString()+" --> --> test04b接收消息：" + message);
    }

    /**
     * Queue 消费者事务
     * @param message
     * @throws Exception
     */
    @Transactional
    @JmsListener(destination = "test05", containerFactory = "queueListenerTanFactory")
    public void receiveTest05(ActiveMQTextMessage message) throws Exception {
        System.out.println(new Date().toString()+" --> --> test05接收消息：" + message);
        throw new RuntimeException("Queue 消费者事务测试 RuntimeException");
    }

    /**
     * Queue 提供者事务
     * @param message
     * @throws Exception
     */
    @JmsListener(destination = "test06")
    public void receiveTest06(ActiveMQTextMessage message) throws Exception {
        System.out.println(new Date().toString()+" --> --> test06接收消息：" + message);
    }

    /**
     * Queue 签收
     * @param message
     * @throws Exception
     */
    @JmsListener(destination = "test07", containerFactory = "queueListenerAckFactory")
    public void receiveTest07(ActiveMQTextMessage message) throws Exception {
        System.out.println(new Date().toString()+" --> --> test07接收消息：" + message);
        message.acknowledge();
    }

    /**
     * Queue 重复策略消息发送，默认 6 次
     * @param message
     * @throws Exception
     */
    @JmsListener(destination = "test08")
    public void receiveTest08(ActiveMQTextMessage message) throws Exception {
        System.out.println(new Date().toString()+" --> --> test08接收消息：" + message);
        throw new RuntimeException("Queue 消费者事务测试 RuntimeException");
    }

    /**
     * Queue 异步发送，错误返回
     * @param message
     * @throws Exception
     */
    @JmsListener(destination = "test09")
    public void receiveTest09(ActiveMQTextMessage message) throws Exception {
        System.out.println(new Date().toString()+" --> --> test09接收消息：" + message);
        //throw new RuntimeException("Queue 消费者事务测试 RuntimeException");
    }


}
