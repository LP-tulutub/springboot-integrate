package com.boot.test;

import com.boot.service.JmsMessageService;
import com.boot.service.MessageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class MyTest {
    
    @Value("${mymq}")
    private String topicName;
    
    @Autowired
    private MessageService messageService;
    @Autowired
    private JmsMessageService jmsMessageService;

    @Test
    public void test01(){
        this.messageService.setJMSMessageMe(topicName);
    }

    @Test
    public void test02(){
        this.messageService.test02("test02");
    }

    @Test
    public void test03(){
        this.jmsMessageService.test03("test03");
    }

    @Test
    public void test03b(){
        this.jmsMessageService.test03b("test03");
    }

    @Test
    public void test04(){
        this.jmsMessageService.test04("test04");
    }

    @Test
    public void test04b(){
        this.jmsMessageService.test04b("test04");
    }

    @Test
    public void test05(){
        this.jmsMessageService.test05("test05");
    }

    @Test
    public void test06(){
        this.jmsMessageService.test06("test06");
    }

    @Test
    public void test07(){
        this.jmsMessageService.test07("test07");
    }

    @Test
    public void test08(){
        this.jmsMessageService.test08("test08");
    }

    @Test
    public void test09(){
        this.jmsMessageService.test09("test09");
    }

    @Test
    public void test09b(){
        this.jmsMessageService.test09b("test09");
    }

    @Test
    public void test10(){
        this.jmsMessageService.test10("test10");
    }

}
