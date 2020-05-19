package com.boot.test;

import com.boot.producer.QueueProducer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
//@WebAppConfiguration
public class TestActiveMQ {

    @Resource
    private QueueProducer queueProducer;

    @Test
    public void testSend(){
        this.queueProducer.produceMessage();
    }
}
