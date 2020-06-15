package com.boot.start.controller;

import com.boot.start.pojo.MyUser;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ProducerAllController {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @RequestMapping("/test09")
    @ResponseBody
    public String test09(){

        Message<MyUser> message = MessageBuilder.withPayload(new MyUser(1, "张三", 22, "飞翔")).build();
        Message<MyUser> message2 = MessageBuilder.withPayload(new MyUser(2, "李四", 23, "起飞")).build();
        Message<MyUser> message3 = MessageBuilder.withPayload(new MyUser(3, "王五", 30, "飞天")).build();
        this.rocketMQTemplate.sendMessageInTransaction("springboot-mq-test09", message, 1);
        this.rocketMQTemplate.sendMessageInTransaction("springboot-mq-test09", message2, 2);
        this.rocketMQTemplate.sendMessageInTransaction("springboot-mq-test09", message3, 3);

        return "OK";
    }

}
