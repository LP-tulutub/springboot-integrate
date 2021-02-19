package com.boot.test;

import com.baomidou.mybatisplus.core.toolkit.AES;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MySecurity {

    @Test
    public void myAESSecurity(){
        // 生成 16 位随机 AES 密钥
        String randomKey = AES.generateRandomKey();

        // 随机密钥加密
        String result = AES.encrypt("jdbc:mysql://localhost:3306/mp?characterEncoding=utf-8&useSSL=true&serverTimezone=GMT%2b8&useUnicode=true", randomKey);
        System.out.println("url: " + result);
        result = AES.encrypt("root", randomKey);
        System.out.println("username: " + result);
        result = AES.encrypt("A18716296148", randomKey);
        System.out.println("password: " + result);
        System.out.println("randomKey: " + randomKey);
    }

}
