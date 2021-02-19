package com.boot.test;

import com.boot.AppRedisCluster;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


/**
 * Spring Data Redis测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes= AppRedisCluster.class)
public class RedisSonTest {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	@Resource
	private RedissonClient redissonClient;

	/**
	 * 添加一个字符串
	 */
	@Test
	public void testSet() {
		this.redisTemplate.opsForValue().set("springBootKey", "测试是否保存到redis集群");
	}

	/**
	 * 获取一个字符串
	 */
	@Test
	public void testGet(){
		String value = (String)this.redisTemplate.opsForValue().get("springBootKey");
		System.out.println(value);
	}

	// 可重入锁、公平锁、联锁、红锁、读写锁、信号量、可过期信号量、闭锁
	/**
	 * 可重入锁(非公平)
	 */
	@Test
	public void test() throws InterruptedException {
		//创建锁
		RLock helloLock = redissonClient.getLock("hello");
		System.out.println("完成创建锁");
		//加锁
		helloLock.lock();
		try {
			System.out.println("进入锁");
			Thread.sleep(1000 * 40);
		} finally {
			System.out.println("释放锁");
			//释放锁
			helloLock.unlock();
		}
	}

	@Test
	public void test2() throws InterruptedException {
		//创建锁
		RLock helloLock = redissonClient.getLock("hello");
		System.out.println("完成创建锁2");
		//加锁
		helloLock.lock();
		try {
			System.out.println("进入锁2");
			Thread.sleep(1000 * 40);
		} finally {
			System.out.println("释放锁2");
			//释放锁
			helloLock.unlock();
		}
	}

    @Test
    public void test3() throws InterruptedException {
        //创建锁
        RLock helloLock = redissonClient.getLock("hello3");
        System.out.println("完成创建锁3");
        //加锁
        helloLock.lock();
        try {
            System.out.println("进入锁3");
            Thread.sleep(1000 * 40);
        } finally {
			System.out.println("释放锁3");
            //释放锁
            helloLock.unlock();
        }
    }

	/**
	 * 读写锁：
	 *   写 + 读，读被阻塞
	 *   写 + 写：写被阻塞
	 *   读 + 写：写被阻塞
	 *   读 + 读：读没有被阻塞
	 */
	@Test
	public void test4(){
		RReadWriteLock readWriteLock = redissonClient.getReadWriteLock("rw-lock");
		RLock rLock = readWriteLock.writeLock();
		try {
			// 写
			rLock.lock();
			this.redisTemplate.opsForValue().set("rwv", "test4");
			System.out.println("test4 写：test4");
			TimeUnit.SECONDS.sleep(30);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			rLock.unlock();
		}
	}

	@Test
	public void test4w(){
		RReadWriteLock readWriteLock = redissonClient.getReadWriteLock("rw-lock");
		RLock rLock = readWriteLock.writeLock();
		try {
			// 写
			rLock.lock();
			this.redisTemplate.opsForValue().set("rwv", "test4w");
			System.out.println("test4w 写：test4w");
			TimeUnit.SECONDS.sleep(30);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			rLock.unlock();
		}
	}

	@Test
	public void test5(){
		RReadWriteLock readWriteLock = redissonClient.getReadWriteLock("rw-lock");
		RLock rLock = readWriteLock.readLock();
		try {
			// 读
			rLock.lock();
			System.out.println("test5 读：" + this.redisTemplate.opsForValue().get("rwv"));
			TimeUnit.SECONDS.sleep(30);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			rLock.unlock();
		}
	}

	@Test
	public void test5r(){
		RReadWriteLock readWriteLock = redissonClient.getReadWriteLock("rw-lock");
		RLock rLock = readWriteLock.readLock();
		try {
			// 读
			rLock.lock();
			System.out.println("test5r 读：" + this.redisTemplate.opsForValue().get("rwv"));
			TimeUnit.SECONDS.sleep(30);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			rLock.unlock();
		}
	}

	/**
	 * 信号量
	 */
	@Test
	public void test6() throws InterruptedException {
		RSemaphore park = redissonClient.getSemaphore("park");
		park.acquire(1);
		boolean flag = park.tryAcquire(); // 拿不到就是 false，不会被阻塞
		System.out.println("成功获取1个包子：" + flag);
	}

	@Test
	public void test6a() throws InterruptedException {
		RSemaphore park = redissonClient.getSemaphore("park");
		park.acquire(2);
		boolean flag = park.tryAcquire();
		System.out.println("成功获取2个包子：" + flag);

	}

	@Test
	public void test7() {
		RSemaphore park = redissonClient.getSemaphore("park");
		System.out.println("做1个包子");
		park.release(1);

	}

	@Test
	public void test7b() {
		RSemaphore park = redissonClient.getSemaphore("park");
		System.out.println("做2个包子");
		park.release(2);

	}

	/**
	 * 闭锁
	 */
	@Test
	public void test8() throws InterruptedException {
		RCountDownLatch door = redissonClient.getCountDownLatch("door");
		door.trySetCount(2);
		door.await(); // 等待闭锁完成(计数为 0 时继续执行后面)
		System.out.println("锁门了，下班");
	}

	@Test
	public void test9() {
		RCountDownLatch door = redissonClient.getCountDownLatch("door");
		door.countDown(); // 计数-1
	}

}
