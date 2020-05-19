package com.boot.service;

import com.boot.config.MyBean;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ScheduledMessage;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.jms.*;
import java.util.HashMap;


@Service
public class JmsMessageService {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;
    @Autowired
    private MyBean myBean;

    /**
     * Queue 持久化传输；发送消息后开启消费者能接收到
     * @param mqName
     */
    public void test03(String mqName) {
        JmsTemplate template = jmsMessagingTemplate.getJmsTemplate();
        // 设置 template 配置是否生效
        assert template != null;
        template.setExplicitQosEnabled(true);
        // 设置持久化 PERSISTENT、非持久化 NON_PERSISTENT
        template.setDeliveryMode(DeliveryMode.PERSISTENT);
        // 设置优先级
        template.setPriority(2);
        // 设置消息过期时间，0 为永远不过期
        template.setTimeToLive(0);
        // 设置Queue、Topic
        template.setPubSubDomain(false);

        this.jmsMessagingTemplate.convertAndSend(new ActiveMQQueue(mqName), "test03发送的内容");

    }

    /**
     * Queue 非持久化消息过期丢失传输；无法接收消息情况：消费者过期没有接收、ActiveMQ Down 掉了
     * @param mqName
     */
    public void test03b(String mqName) {
        JmsTemplate template = this.jmsMessagingTemplate.getJmsTemplate();
        // 设置 template 配置是否生效
        assert template != null;
        template.setExplicitQosEnabled(true);
        // 设置持久化 PERSISTENT、非持久化 NON_PERSISTENT
        template.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        // 设置优先级
        template.setPriority(2);
        // 设置消息过期时间，0 为永远不过期
        template.setTimeToLive(1000);
        // 设置Queue、Topic
        template.setPubSubDomain(false);

        this.jmsMessagingTemplate.convertAndSend(new ActiveMQQueue(mqName), "test03b发送的内容");
    }

    /**
     * Topic 持久化传输；先开启消费者订阅，再发送消息
     * @param mqName
     */
    public void test04(String mqName) {
        JmsTemplate template = this.jmsMessagingTemplate.getJmsTemplate();
        // 设置 template 配置是否生效
        assert template != null;
        template.setExplicitQosEnabled(true);
        // 设置持久化 PERSISTENT、非持久化 NON_PERSISTENT
        template.setDeliveryMode(DeliveryMode.PERSISTENT);
        template.setDeliveryPersistent(true);
        // 设置优先级
        template.setPriority(2);
        // 设置消息过期时间，0 为永远不过期
        template.setTimeToLive(0);
        // 设置Queue、Topic
        template.setPubSubDomain(true);

        HashMap<String, Object> map = new HashMap<>();
        map.put("myProperty", "myProperty内容");

        this.jmsMessagingTemplate.convertAndSend(new ActiveMQTopic(mqName), "test04发送的内容", map);


    }

    /**
     * Topic 非持久化传输；无法接收消息情况：ActiveMQ Down 掉了
     * @param mqName
     */
    public void test04b(String mqName) {
        JmsTemplate template = this.jmsMessagingTemplate.getJmsTemplate();
        // 设置 template 配置是否生效
        assert template != null;
        template.setExplicitQosEnabled(true);
        // 设置持久化 PERSISTENT、非持久化 NON_PERSISTENT
        template.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        template.setDeliveryPersistent(false);
        // 设置优先级
        template.setPriority(2);
        // 设置消息过期时间，0 为永远不过期
        template.setTimeToLive(0);
        // 设置Queue、Topic
        template.setPubSubDomain(true);

        this.jmsMessagingTemplate.convertAndSend(new ActiveMQTopic(mqName), "test04发送的内容");

    }

    /**
     * Queue 消费者事务；无法接收消息情况：一个消息多次重新执行消费者方法
     * @param mqName
     */
    public void test05(String mqName) {
        JmsTemplate template = this.jmsMessagingTemplate.getJmsTemplate();
        // 设置 template 配置是否生效
        assert template != null;
        template.setExplicitQosEnabled(true);
        // 设置持久化 PERSISTENT、非持久化 NON_PERSISTENT
        template.setDeliveryMode(DeliveryMode.PERSISTENT);
        template.setDeliveryPersistent(true);
        // 设置优先级
        template.setPriority(2);
        // 设置消息过期时间，0 为永远不过期
        template.setTimeToLive(0);
        // 设置Queue、Topic
        template.setPubSubDomain(false);

        this.jmsMessagingTemplate.setDefaultDestinationName(mqName);
        this.jmsMessagingTemplate.setDefaultDestination(new ActiveMQQueue(mqName));
        for (int i=0; i<3; i++){
            this.jmsMessagingTemplate.convertAndSend("test05发送的内容-" + i);
        }

    }

    /**
     * Queue 提供者事务；无法接收消息情况：提供者整个方法有错误，失效。注意需要在启动类上添加 @EnableTransactionManagement
     * @param mqName
     */
    @Transactional(value = "txManager1")
    public void test06(String mqName) {
        JmsTemplate template = this.jmsMessagingTemplate.getJmsTemplate();
        // 设置 template 配置是否生效
        assert template != null;
        template.setExplicitQosEnabled(true);
        // 设置持久化 PERSISTENT、非持久化 NON_PERSISTENT
        template.setDeliveryMode(DeliveryMode.PERSISTENT);
        template.setDeliveryPersistent(true);
        // 设置优先级
        template.setPriority(2);
        // 设置消息过期时间，0 为永远不过期
        template.setTimeToLive(0);
        // 设置Queue、Topic
        template.setPubSubDomain(false);
        // 设置事务，默认 false 关闭状态
        template.setSessionTransacted(true);

        this.jmsMessagingTemplate.setDefaultDestinationName(mqName);
        this.jmsMessagingTemplate.setDefaultDestination(new ActiveMQQueue(mqName));
        for (int i=0; i<3; i++){
            this.jmsMessagingTemplate.convertAndSend("test06发送的内容-" + i);
            if (i==1) throw new RuntimeException("Queue 提供者事务测试 RuntimeException");
        }

    }

    /**
     * Queue 提供者签收设置；重复接收消息情况：消费者没有签收
     * @param mqName
     */
    public void test07(String mqName) {
        JmsTemplate template = this.jmsMessagingTemplate.getJmsTemplate();
        // 设置 template 配置是否生效
        assert template != null;
        template.setExplicitQosEnabled(true);
        // 设置持久化 PERSISTENT、非持久化 NON_PERSISTENT
        template.setDeliveryMode(DeliveryMode.PERSISTENT);
        template.setDeliveryPersistent(true);
        // 设置优先级
        template.setPriority(2);
        // 设置消息过期时间，0 为永远不过期
        template.setTimeToLive(0);
        // 设置Queue、Topic
        template.setPubSubDomain(false);
        // 设置手动签收
        template.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);

        this.jmsMessagingTemplate.setDefaultDestinationName(mqName);
        this.jmsMessagingTemplate.setDefaultDestination(new ActiveMQQueue(mqName));
        for (int i=0; i<3; i++){
            this.jmsMessagingTemplate.convertAndSend("test07发送的内容-" + i);
        }

    }

    /**
     * Queue 重复策略消息发送；
     * @param mqName
     */
    public void test08(String mqName) {
        JmsTemplate myJmsTemplate = myBean.myJmsTemplate();

        this.jmsMessagingTemplate.setDefaultDestinationName(mqName);
        this.jmsMessagingTemplate.setDefaultDestination(new ActiveMQQueue(mqName));

        this.jmsMessagingTemplate.convertAndSend("test08发送的内容");

    }

    /**
     * Queue 同步发送；确认发送成功后再继续
     * @param mqName
     */
    public void test09(String mqName) {
        JmsTemplate myJmsTemplate = myBean.myJmsTemplate();
        // 设置返回时间
        myJmsTemplate.setReceiveTimeout(5000);
        // 设置 Receive Down 了后同时删除消息
        myJmsTemplate.setTimeToLive(5000);

        this.jmsMessagingTemplate.setDefaultDestinationName(mqName);
        this.jmsMessagingTemplate.setDefaultDestination(new ActiveMQQueue(mqName));

        Object receive = this.jmsMessagingTemplate.convertSendAndReceive("test09发送的内容", Object.class);
        System.out.println(receive);
    }

    /**
     * Queue 异步发送；丢掉部分消息高性能发送
     * @param mqName
     */
    public void test09b(String mqName) {
        JmsTemplate myJmsTemplate = myBean.myJmsTemplate();
        // 设置返回时间
        myJmsTemplate.setReceiveTimeout(5000);
        // 设置 Receive Down 了后同时删除消息
        myJmsTemplate.setTimeToLive(5000);

        // 设置异步
        PooledConnectionFactory pooledConnectionFactory = (PooledConnectionFactory) myJmsTemplate.getConnectionFactory();
        ActiveMQConnectionFactory activeMQConnectionFactory = (ActiveMQConnectionFactory) pooledConnectionFactory.getConnectionFactory();
        activeMQConnectionFactory.setUseAsyncSend(true);

        this.jmsMessagingTemplate.setDefaultDestinationName(mqName);
        this.jmsMessagingTemplate.setDefaultDestination(new ActiveMQQueue(mqName));

        Object receive = this.jmsMessagingTemplate.convertSendAndReceive("test09b发送的内容", Object.class);
        System.out.println(receive);

    }

    /**
     * Queue 延时、定时发送
     * @param mqName
     */
    public void test10(String mqName) {
        JmsTemplate myJmsTemplate = myBean.myJmsTemplate();

        this.jmsMessagingTemplate.setDefaultDestinationName(mqName);
        this.jmsMessagingTemplate.setDefaultDestination(new ActiveMQQueue(mqName));

        // 设置延时
        HashMap<String, Object> map = new HashMap<>();
        map.put(ScheduledMessage.AMQ_SCHEDULED_DELAY, 3 * 1000);
        map.put(ScheduledMessage.AMQ_SCHEDULED_PERIOD, 3 * 1000);
        map.put(ScheduledMessage.AMQ_SCHEDULED_REPEAT, 3);

        this.jmsMessagingTemplate.convertAndSend(mqName, "test10发送的内容", map);

    }


}
