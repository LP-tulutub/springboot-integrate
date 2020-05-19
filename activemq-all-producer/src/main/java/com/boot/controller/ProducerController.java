package com.boot.controller;

import com.boot.service.JmsMessageService;
import com.boot.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/message")
public class ProducerController {

    @Autowired
    private MessageService messageService;
    @Autowired
    private JmsMessageService jmsMessageService;
    @Value("${mymq}")
    private String mqName;

    @RequestMapping("/test01")
    public String test01(){
        this.messageService.setJMSMessageMe(mqName);
        return "succeed";
    }

    @RequestMapping("/test09")
    public String test09(){
        this.jmsMessageService.test09("test09");
        return "succeed";
    }

}
