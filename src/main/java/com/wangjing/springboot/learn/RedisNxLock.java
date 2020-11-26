/**
 * @projectName springboot-learn
 * @package com.wangjing.springboot.learn
 * @className com.wangjing.springboot.learn.RedisNxLock
 * @copyright Copyright 2020 Thuisoft, Inc. All rights reserved.
 */
package com.wangjing.springboot.learn;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import com.sun.org.apache.xpath.internal.operations.Bool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import lombok.SneakyThrows;

/**
 * RedisNxLock
 *
 * @description redisnx分布式锁
 * @author wangjing
 * @date 2020/11/26 14:46
 * @version v1.0.0
 */
@Service
public class RedisNxLock {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @SneakyThrows
    public void test() {
        CountDownLatch countDownLatch = new CountDownLatch(10);
        for(int i = 0; i < 10; i++) {
            Thread thread = new Thread(new GetLock(stringRedisTemplate, countDownLatch), "redisNxThread"+i);
            thread.start();
        }
        System.out.println("main wait");
        countDownLatch.await();
        System.out.println("main over");
    }

}

class GetLock implements Runnable {

    private StringRedisTemplate stringRedisTemplate;

    private CountDownLatch countDownLatch;

    private String lock = "lock";

    public GetLock(StringRedisTemplate stringRedisTemplate, CountDownLatch countDownLatch) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.countDownLatch = countDownLatch;
    }

    @SneakyThrows
    @Override
    public void run() {
        while(true) {
            Boolean setLock = stringRedisTemplate.opsForValue()
                .setIfAbsent(lock, "true", 60 * 5, TimeUnit.SECONDS);
            if(setLock.equals(Boolean.TRUE)) {
                System.out.println(Thread.currentThread().getName() + "\tlock success, do business");
                Thread.sleep(1000 * 5);
            } else {
                continue;
            }
            stringRedisTemplate.delete(lock);
            break;
        }
        countDownLatch.countDown();
        System.out.println(Thread.currentThread().getName() + "\tunlock");
    }
}