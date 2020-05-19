package com.boot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;

import javax.jms.ConnectionFactory;

@EnableJms
@Configuration
public class MyListenerFactory {

    /**
     * Queue
     * @param connectionFactory
     * @return
     */
    @Bean(name = "queueListenerFactory")
    public DefaultJmsListenerContainerFactory queueListenerFactory(ConnectionFactory connectionFactory){

        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();

        factory.setConnectionFactory(connectionFactory);
        factory.setPubSubDomain(false);

        factory.setConcurrency("3-10");

        return factory;

    }

    /**
     * Topic
     * @param connectionFactory
     * @return
     */
    @Bean(name = "topicListenerFactory")
    public DefaultJmsListenerContainerFactory topicListenerFactory(ConnectionFactory connectionFactory){

        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();

        factory.setConnectionFactory(connectionFactory);
        factory.setPubSubDomain(true);

        return factory;

    }

    /**
     * Topic 持久化
     * @param connectionFactory
     * @return
     */
    @Bean(name = "topicListenerSubFactory")
    public DefaultJmsListenerContainerFactory topicListenerSubFactory(ConnectionFactory connectionFactory){

        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setPubSubDomain(true);
        factory.setSubscriptionDurable(true);
        factory.setClientId("test04b");

        // 重新连接 ActiveMQ，最多 3 次
        /*FixedBackOff fixedBackOff = new FixedBackOff();
        fixedBackOff.setMaxAttempts(3);
        factory.setBackOff(fixedBackOff);*/

        return factory;

    }

    /**
     * Queue 消费者事务，如果消费者报错，则会多次重新读取消息
     * @param connectionFactory
     * @return
     */
    @Bean(name = "queueListenerTanFactory")
    public DefaultJmsListenerContainerFactory queueListenerTanFactory(ConnectionFactory connectionFactory){

        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();

        factory.setConnectionFactory(connectionFactory);
        factory.setPubSubDomain(false);
        factory.setSessionTransacted(true);

        return factory;

    }

    /**
     * Queue 手动签收，默认会签收，设置为4不签收，设置为 CLIENT_ACKNOWLEDGE 手动签收
     * @param connectionFactory
     * @return
     */
    @Bean(name = "queueListenerAckFactory")
    public DefaultJmsListenerContainerFactory queueListenerAckFactory(ConnectionFactory connectionFactory){

        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();

        factory.setConnectionFactory(connectionFactory);
        factory.setPubSubDomain(false);
        factory.setSessionAcknowledgeMode(4);

        //factory.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);
        return factory;

    }


}
