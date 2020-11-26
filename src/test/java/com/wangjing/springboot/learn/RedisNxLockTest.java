/**
 * @projectName springboot-learn
 * @package com.wangjing.springboot.learn
 * @className com.wangjing.springboot.learn.RedisNxLockTest
 * @copyright Copyright 2020 Thuisoft, Inc. All rights reserved.
 */
package com.wangjing.springboot.learn;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * RedisNxLockTest
 *
 * @description redis
 * @author wangjing
 * @date 2020/11/26 14:53
 * @version v1.0.0
 */
public class RedisNxLockTest extends SpringbootLearnApplicationTests{

    @Autowired
    private RedisNxLock redisNxLock;

    @Test
    public void test() {
        redisNxLock.test();
    }
}
