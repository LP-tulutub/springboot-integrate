package com.boot.redis;

import org.redisson.api.RedissonClient;
import org.redisson.spring.transaction.RedissonTransactionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class RedisTransactionalConfig {

    @Bean(name = "myRedissonTransactionManager")
    public RedissonTransactionManager myRedissonTransactionManager(RedissonClient redissonClient){
        return new RedissonTransactionManager(redissonClient);
    }

}
