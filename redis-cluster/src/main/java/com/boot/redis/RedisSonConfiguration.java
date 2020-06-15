package com.boot.redis;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.spring.data.connection.RedissonConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.IOException;


@Configuration
public class RedisSonConfiguration {

    @Bean
    public RedissonClient redisson() throws IOException {
        Config config = Config.fromYAML(new ClassPathResource("redisson.yml").getInputStream());
        RedissonClient redisson = Redisson.create(config);
        return redisson;
    }

    @Bean
    public RedissonConnectionFactory redissonConnectionFactory(RedissonClient redisson) {
        return new RedissonConnectionFactory(redisson);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedissonConnectionFactory redissonConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redissonConnectionFactory);
        // 序列化类
        //MyRedisSerializer myRedisSerializer = new MyRedisSerializer();
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        // key序列化方式
        template.setKeySerializer(stringRedisSerializer);
        // value序列化
        template.setValueSerializer(stringRedisSerializer);
        // value hashmap序列化
        template.setHashKeySerializer(stringRedisSerializer);
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        // 支持事务
        template.setEnableTransactionSupport(true);
        return template;
    }

}
