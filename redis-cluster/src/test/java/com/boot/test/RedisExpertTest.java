package com.boot.test;

import com.boot.AppRedisCluster;
import com.boot.service.RedisService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RMap;
import org.redisson.api.RTransaction;
import org.redisson.api.RedissonClient;
import org.redisson.api.TransactionOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AppRedisCluster.class)
public class RedisExpertTest {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Resource
    private RedissonClient redissonClient;
    @Autowired
    private RedisService redisService;

    /**
     * RTransaction 事务；如果 commit 前出现错误，不会修改 redis
     */
    @Test
    public void testTransactional01(){
        RTransaction transaction = this.redissonClient.createTransaction(TransactionOptions.defaults());
        RMap<String, Object> rMap = transaction.getMap("testTransactional");
        try {
            rMap.put("testTransactional:01", "v1");
            rMap.put("testTransactional:02", "v2");
            // 如果下面注释取消，则不会修改 redis
            //if (true) throw new RuntimeException("redisson 测试事务");
            rMap.put("testTransactional:03", "v3");
            transaction.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * @Transactional 事务，有该注释的方法出现错误，该方法所有操作回滚
     */
    @Test
    public void testTransactional02(){
        this.redisService.expertTransactionalTest02();
    }

    /**
     * 关于连接池：
     * RedissonConnectionFactory 核心 RedissonClient 的 Config 有 ClusterServersConfig
     * ClusterServersConfig 这个类继承的父类 BaseMasterSlaveServersConfig 有 pool 的相关默认设置
     */



}
