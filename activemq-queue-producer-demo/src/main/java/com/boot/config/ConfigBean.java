package com.boot.config;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.stereotype.Component;

@Component
@EnableJms // 开启jms 适配
public class ConfigBean {

    @Value("${myqueue}")
    private String myQueue ;    // 注入配置文件中的 myqueue

    @Bean   // bean id=""  class="…"
    public ActiveMQQueue queue(){
        return  new ActiveMQQueue(myQueue);
    }
}
