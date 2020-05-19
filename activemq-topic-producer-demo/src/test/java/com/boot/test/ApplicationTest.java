package com.boot.test;

import com.boot.producer.TopicProducer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTest {

    @Resource
    private TopicProducer topicProducer;

    @Test
    public void test() {
        this.topicProducer.produceTopic();
    }
}
