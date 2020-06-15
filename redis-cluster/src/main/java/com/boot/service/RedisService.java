package com.boot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * @Transactional 事务，有该注释的方法出现错误，该方法所有操作回滚
     */
    @Transactional(value = "myRedissonTransactionManager")
    public void expertTransactionalTest02(){
        this.redisTemplate.opsForValue().set("testTransactional:01", "v1");
        this.redisTemplate.opsForValue().set("testTransactional:02", "v2");
        // 取消 @Transactional，如果下面一条注释取消，那么会插入 2 条进入 redis
        //if (true) throw new RuntimeException("redisson 测试事务");
        this.redisTemplate.opsForValue().set("testTransactional:03", "v3");
    }

}
