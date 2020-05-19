package com.boot.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.connection.SingleConnectionFactory;

import javax.jms.ConnectionFactory;

@EnableJms
@Configuration
public class MyListenerFactory {

    /**
     * Queue
     * @param connectionFactory
     * @return
     */
    @Bean
    public DefaultJmsListenerContainerFactory queueListenerFactory(ConnectionFactory connectionFactory){

        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();

        factory.setConnectionFactory(connectionFactory);
        factory.setPubSubDomain(false);

        return factory;

    }

    /**
     * Topic
     * @param connectionFactory
     * @return
     */
    @Bean
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
    @Bean
    public DefaultJmsListenerContainerFactory topicListenerSubFactory(SingleConnectionFactory connectionFactory){

        connectionFactory.setClientId("test04b");

        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setPubSubDomain(true);
        factory.setSubscriptionDurable(true);

        return factory;

    }

    /**
     * Queue 消费者事务，如果消费者报错，则会多次重新读取消息
     * @param connectionFactory
     * @return
     */
    @Bean
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
    @Bean
    public DefaultJmsListenerContainerFactory queueListenerAckFactory(ConnectionFactory connectionFactory){

        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();

        factory.setConnectionFactory(connectionFactory);
        factory.setPubSubDomain(false);
        factory.setSessionAcknowledgeMode(4);

        //factory.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);
        return factory;

    }

    /**
     * Queue 异步发送
     * @param connectionFactory
     * @return
     */


}
