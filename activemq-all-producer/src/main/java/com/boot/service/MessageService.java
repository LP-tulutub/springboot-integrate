package com.boot.service;


import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;


import javax.jms.*;
import java.util.HashMap;

@Component
public class MessageService {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    /**
     * 实现jms消息传递互通
     * @param mqName
     */
    public void setJMSMessageMe(String mqName) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("map", "ok");
        this.jmsMessagingTemplate.convertAndSend(new ActiveMQQueue(mqName), map);
        /*MessagePostProcessor messagePostProcessor = new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws JMSException {
                // 设置Queue、Topic
                message.setJMSDestination(new ActiveMQTopic(mqName));
                // 设置0持久化
                message.setJMSDeliveryMode(0);
                // 设置消息过期时间，0 为不过期
                message.setJMSExpiration(0);
                // 设置消息的优先级，越大越优先，大于 4 为紧急实现
                message.setJMSPriority(4);
                // 设置属性标注
                message.setBooleanProperty("test", true);
                // 手动接收到消息
                //message.acknowledge();
                return message;
            }
        };*/
    }

    /**
     * Queue 传输，@JmsListener 接收
     * @param mqName
     */
    public void test02(String mqName) {

        try {
            Connection connection = this.jmsMessagingTemplate.getConnectionFactory().createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer producer = session.createProducer(session.createQueue(mqName));

            Message message = session.createMessage();
            // 设置0持久化
            message.setJMSDeliveryMode(0);
            // 设置消息过期时间，0 为不过期
            message.setJMSExpiration(0);
            // 设置消息的优先级，越大越优先，大于 4 为紧急实现
            message.setJMSPriority(4);
            // 设置属性标注
            message.setBooleanProperty("test", true);

            producer.send(message);

            producer.close();
            session.close();
            connection.close();

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }



}
