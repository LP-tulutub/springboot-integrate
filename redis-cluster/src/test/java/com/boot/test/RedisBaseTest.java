package com.boot.test;

import com.boot.AppRedisCluster;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AppRedisCluster.class)
public class RedisBaseTest {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Resource
    private RedissonClient redissonClient;

    @Test
    public void testKey() {
        // 查询所有 key
        this.redisTemplate.opsForValue().set("testKey", "expire");
        System.out.println("keys *: " + this.redisTemplate.keys("*"));
        // 是否存在，存在为 true
        System.out.println("exists [key名称]:" + this.redisTemplate.hasKey("k1"));
        // 设置过期时间
        System.out.println("expire [key名称] [秒数字]: " + this.redisTemplate.expire("testKey", 10, TimeUnit.SECONDS));
        // 没过期返回 true，过期返回 false
        System.out.println("ttl [key名称]: " + this.redisTemplate.persist("testKey"));
        // 查看 key 的类型
        System.out.println("type [key名称]: " + this.redisTemplate.type("testKey"));

    }

    @Test
    public void testString() {
        // 新增、获取、删除，新增重复键覆盖
        this.redisTemplate.opsForValue().set("testString", "set");
        System.out.println("set [key名称] [value值]: testString");
        System.out.println("get [key名称] [value值]: " + this.redisTemplate.opsForValue().get("testString"));
        System.out.println("del [key名称] [value值]: " + this.redisTemplate.delete("testString"));
        // 在 key 的 value 后加上新的字符串
        this.redisTemplate.opsForValue().set("testString", "set");
        System.out.println("append [key名称] [value值]: " + this.redisTemplate.opsForValue().append("testString", "2"));
        // 每使用一次，key 的 value + 1 或 value - 1，起始默认值为 0
        this.redisTemplate.opsForValue().set("testString", "10");
        System.out.println("incr [key名称]: " + this.redisTemplate.opsForValue().increment("testString"));
        System.out.println("decr [key名称]: " + this.redisTemplate.opsForValue().decrement("testString"));
        // 每次 key 的 value +- 自定义数字
        System.out.println("incrby [key名称] [增数字]: " + this.redisTemplate.opsForValue().increment("testString", 2));
        System.out.println("decrby [key名称] [减数字]: " + this.redisTemplate.opsForValue().decrement("testString", 2));
        // 范围查询、固定位置插入覆盖
        System.out.println("getrange [key名称] 0 -1: " + this.redisTemplate.opsForValue().get("testString", 0, -1));
        this.redisTemplate.opsForValue().set("testString", "6", 1);
        System.out.println("setrange [key名称] [插入索引位置数字] [vaule值]: " + this.redisTemplate.opsForValue().get("testString"));
        //  一个命令设置多个 key，一个命令获取多个 key
        HashMap<String, Object> map = new HashMap<>();
        map.put("{map}:01", "v1");
        map.put("{map}:02", "v2");
        List<String> list = new ArrayList<>();
        list.add("{map}:01");
        list.add("{map}:02");
        this.redisTemplate.opsForValue().multiSet(map);
        System.out.println("mget [key名称] [vaule值] [key名称] [vaule值]: " + this.redisTemplate.opsForValue().multiGet(list));
        // 获取 key 的旧 value，同时设置新 value
        System.out.println("getset [key名称] [vaule值]: " + this.redisTemplate.opsForValue().getAndSet("testString", "over"));
    }

    @Test
    public void testList() {
        if (this.redisTemplate.hasKey("testList"))
            this.redisTemplate.delete("testList");
        this.redisTemplate.opsForList().rightPushAll("testList", "1", "2", "3", "4", "5");
        // 按 0 位置插入，最后一个位置添加
        System.out.println("lpush [list名称] [vaule值] [vaule值]: " + this.redisTemplate.opsForList().leftPush("testList", "0"));
        System.out.println("lpush [list名称] [vaule值] [vaule值]: " + this.redisTemplate.opsForList().rightPush("testList", "6"));
        // 查看表
        System.out.println("lrange [list名称] 0 -1: " + this.redisTemplate.opsForList().range("testList", 0, -1));
        // list 的长度
        System.out.println("llen [list名称]: " + this.redisTemplate.opsForList().size("testList"));
        // 删除值
        System.out.println("lrem [list名称] [删除个数] [value值]: " + this.redisTemplate.opsForList().remove("testList", 1, "4"));
        // 范围保留 value
        this.redisTemplate.opsForList().trim("testList", 0, 3);
        System.out.println("ltrim [list名称] [开始索引值] [结束索引值]: " + this.redisTemplate.opsForList().range("testList", 0, -1));
        // 移除列表的最后一个元素，并将该元素添加到另一个列表并返回
        if (this.redisTemplate.hasKey("{testList}:01")) {
            this.redisTemplate.delete("{testList}:01");
            this.redisTemplate.delete("{testList}:02");
        }
        this.redisTemplate.opsForList().rightPushAll("{testList}:01", "1", "2", "3", "4", "5");
        this.redisTemplate.opsForList().rightPushAll("{testList}:02", "1", "2", "3", "4", "5");
        System.out.println("rpoplpush [list名称] [list名称]: " + this.redisTemplate.opsForList().rightPopAndLeftPush("{testList}:01", "{testList}:02"));
        // 修改 list 的某个 value
        this.redisTemplate.opsForList().set("testList", 0, "-1");
        System.out.println("lset [list名称] [索引值] [value值]: " + this.redisTemplate.opsForList().range("testList", 0, -1));
        // 在 list 某个已有值的前后再添加具体值，在 1 的左边插入 0
        System.out.println("linsert [list名称] before/after [value值] [value值]： " + this.redisTemplate.opsForList().leftPush("testList", "1", "0"));

    }

    @Test
    public void testSet() {
        // 给 set 加入值，重复的跳过
        System.out.println("sadd [set名称] [value值] [value值]: " + this.redisTemplate.opsForSet().add("testSet", "1", "2", "3", "3", "4", "5"));
        // 查看 set，检查 set 是否存在某个 value
        System.out.println("smembers [set名称]: " + this.redisTemplate.opsForSet().members("testSet"));
        System.out.println("sismember [set名称] [value值]: " + this.redisTemplate.opsForSet().isMember("testSet", "3"));
        // 获取集合元素个数
        System.out.println("scard [set名称]: " + this.redisTemplate.opsForSet().size("testSet"));
        // 删除 set 的某个值
        System.out.println("srem [set名称] [value值]: " + this.redisTemplate.opsForSet().remove("testSet", "2"));
        // 随机从 set 中取出几个值
        System.out.println("srandmember [set名称] [取出个数]: " + this.redisTemplate.opsForSet().randomMember("testSet"));
        // 出栈 1 个，redis 版本 3.2+
        /*System.out.println("spop [set名称]: " + this.redisTemplate.opsForSet().pop("testSet", 1));*/
        // 前面 set 的 value 转移给后面的 set
        if (this.redisTemplate.hasKey("{testSet}:01")) {
            this.redisTemplate.delete("{testSet}:01");
            this.redisTemplate.delete("{testSet}:02");
        }
        this.redisTemplate.opsForSet().add("{testSet}:01", "3", "4", "5", "6");
        this.redisTemplate.opsForSet().add("{testSet}:02", "1", "2", "3", "4");
        System.out.println("smove [set名称] [set名称] [value值]: " + this.redisTemplate.opsForSet().move("{testSet}:01", "5", "{testSet}:02"));
        // 差集、交集、并集
        System.out.println("sdiff [set名称] [set名称]: " + this.redisTemplate.opsForSet().difference("{testSet}:01", "{testSet}:02"));
        System.out.println("sinter [set名称] [set名称]: " + this.redisTemplate.opsForSet().intersect("{testSet}:01", "{testSet}:02"));
        System.out.println("sunion [set名称] [set名称]: " + this.redisTemplate.opsForSet().union("{testSet}:01", "{testSet}:02"));
    }

    @Test
    public void testHash() {
        // 插入数据、获得数据，获取所有 key 与 value
        HashMap<String, Object> map = new HashMap<>();
        map.put("map01", "v1");
        map.put("map02", "v2");
        map.put("map03", "v3");
        map.put("map04", "v4");
        map.put("map05", "v5");
        map.put("map06", 10);
        this.redisTemplate.opsForHash().putAll("testHash", map);
        System.out.println("hset [hash名称] [key值] [value值]/hget [hash名称] [key值]: " + this.redisTemplate.opsForHash().keys("testHash") + this.redisTemplate.opsForHash().values("testHash"));
        // 删除数据
        System.out.println("hedl [hash名称] [key值] [key值]: " + this.redisTemplate.opsForHash().delete("testHash", "map02"));
        // hash 表长度
        System.out.println("hlen [hash名称]: " + this.redisTemplate.opsForHash().size("testHash"));
        // 检查 hash 表是否有某个 key
        System.out.println("hexists [hash名称] [key值]: " + this.redisTemplate.opsForHash().hasKey("testHash", "map01"));
        // 每次执行增加一次
        System.out.println("hincrby [hash名称] [key值] [增数字]: " + this.redisTemplate.opsForHash().increment("testHash", "map06", 2));

    }

    @Test
    public void testZSet() {
        // 给 ZSet 加入值，每个值有对应的分数，重复的跳过
        HashSet<ZSetOperations.TypedTuple<Object>> set = new HashSet<>();
        set.add(new DefaultTypedTuple<Object>("1", (double) 60));
        set.add(new DefaultTypedTuple<Object>("2", (double) 70));
        set.add(new DefaultTypedTuple<Object>("3", (double) 80));
        set.add(new DefaultTypedTuple<Object>("4", (double) 90));
        set.add(new DefaultTypedTuple<Object>("5", (double) 100));
        System.out.println("zadd [zset名称] [score值] [value值] [score值] [value值]: " + this.redisTemplate.opsForZSet().add("testZSet", set));
        // 查看全部，包括 score
        System.out.println("zrange [zset名称] 0 -1 whthscores: " + this.redisTemplate.opsForZSet().rangeWithScores("testZSet", 0, -1));
        // 查看全部，不包括 score
        System.out.println("zrange [zset名称] 0 -1: " + this.redisTemplate.opsForZSet().range("testZSet", 0, -1));
        // 根据 socre 获取
        System.out.println("zrangebyscore [zset名称] [socre开始] [socre结束]: " + this.redisTemplate.opsForZSet().rangeByScore("testZSet", 60, 80));
        // 根据 socre 获取，加上限制条件
        System.out.println("zrangebyscore [zset名称] [socre开始] [socre结束] limit [index开始] [index结束]: " + this.redisTemplate.opsForZSet().rangeByScore("testZSet", 60, 80, 1, 2));
        // 根据 value 删除
        System.out.println("zrem [zset名称] [value值]: " + this.redisTemplate.opsForZSet().remove("testZSet", "2"));
        // zset 的长度
        System.out.println("zcard [zset名称]: " + this.redisTemplate.opsForZSet().size("testZSet"));
        // zset 分数范围内的个数
        System.out.println("zcount [zset名称] [socre开始] [socre结束]: " + this.redisTemplate.opsForZSet().count("testZSet", 80, 100));
        // 检查 zset 是否有某个值 value，返回索引，如果没有返回 null
        System.out.println("zrank [zset名称] [value值]: " + this.redisTemplate.opsForZSet().rank("testZSet", "5"));
        // 根据分数范围，逆序查看全部
        System.out.println("zrevrangebyscore [zset名称] [socre开始] [socre结束]: " + this.redisTemplate.opsForZSet().reverseRange("testZSet", 0, 100));

    }
}