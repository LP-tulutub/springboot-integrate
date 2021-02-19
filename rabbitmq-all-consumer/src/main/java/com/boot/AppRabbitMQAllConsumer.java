package com.boot;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableRabbit
@SpringBootApplication
public class AppRabbitMQAllConsumer {
    public static void main(String[] args) {
        SpringApplication.run(AppRabbitMQAllConsumer.class, args);
    }

}

