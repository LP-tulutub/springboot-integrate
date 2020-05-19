package com.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class AppActiveMQAllProducer {
    public static void main(String[] args) {
        SpringApplication.run(AppActiveMQAllProducer.class, args);
    }

}
