package com.boot.tart;



import com.boot.AppRocketMQAllProducer;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AppRocketMQAllProducer.class})
public class Producer {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    /**
     * 单向发送
     */
    @Test
    public void onMessageTest01(){
        this.rocketMQTemplate.convertAndSend("springboot-mq","hello springboot rocketmq");
    }

    /**
     * 同步发送
     */
    @Test
    public void onMessageTest02(){

        SendResult result = this.rocketMQTemplate.syncSend("springboot-mq", "hello springboot rocketmq2");
        log.warn(result.getMsgId() + " " + result.getMessageQueue());

    }




}
