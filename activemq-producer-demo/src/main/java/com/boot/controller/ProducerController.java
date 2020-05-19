package com.boot.controller;


import com.boot.service.QueueMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/message")
public class ProducerController {


    @Autowired
    private QueueMessageService queueMessageService;
    /*
     * 消息生产者
     */
    @GetMapping("/sendmsg")
    public void sendmsg(String msg) {
        // 指定消息发送的目的地及内容
        this.queueMessageService.setQueue("queue", msg);
    }

    @GetMapping("/send")
    public void send(String msg) {
        // 指定消息发送的目的地及内容
        System.out.println(new Date().toString());
        this.queueMessageService.setTopic("topic", msg);
    }



}
