package com.boot.service;


import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.MessagePostProcessor;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;

@Component
public class QueueMessageService {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    /**
     * 使用queue模式注册队列
     * @param queue
     * @param msg
     */
    public void setQueue(String queue, String msg) {
        jmsMessagingTemplate.convertAndSend(new ActiveMQQueue(queue), msg);
    }

    /**
     * 使用topic模式注册队列
     * @param topic
     * @param msg
     */
    public void setTopic(String topic, String msg) {
        jmsMessagingTemplate.convertAndSend(new ActiveMQTopic(topic), msg);
    }


/*    public void test() {
        MessagePostProcessor messagePostProcessor = new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws JMSException {
                message.setJMSDeliveryMode(0);
                message.setJMSPriority(4);
                message.setBooleanProperty("test", true);
                message.acknowledge();
                return null;
            }
        };
    }*/

}
