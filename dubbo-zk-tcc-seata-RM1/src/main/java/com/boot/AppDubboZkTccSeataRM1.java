package com.boot;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
@MapperScan("com.boot.mapper")
public class AppDubboZkTccSeataRM1 {

	public static void main(String[] args) {
		SpringApplication.run(AppDubboZkTccSeataRM1.class, args);
	}
}
