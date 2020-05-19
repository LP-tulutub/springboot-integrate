package com.boot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.JmsTransactionManager;

import javax.jms.ConnectionFactory;

@Configuration
public class MyJmsTransactionManager {

    // 创建事务管理器1
    @Bean(name = "txManager1")
    public JmsTransactionManager txManager(ConnectionFactory connectionFactory) {
        JmsTransactionManager jmsTransactionManager = new JmsTransactionManager(connectionFactory);
        return jmsTransactionManager;
    }


}
