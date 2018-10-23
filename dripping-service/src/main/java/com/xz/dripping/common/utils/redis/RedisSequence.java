package com.xz.dripping.common.utils.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 功能: Redis序列器工具类
 * 日期: 2017/6/21 0021 17:31
 *
 * @author Administrators
 * @since JDK 1.7
 */
public class RedisSequence {

    /**
     * 日志器
     */
    private static final Logger logger = LoggerFactory.getLogger(RedisSequence.class);

    /**
     * Redis管理器
     */
    private RedisManager redisManager;

    public RedisSequence() {
    }

    public RedisSequence(RedisManager redisManager) {
        this.redisManager = redisManager;
    }

    public Long incr(String sequence) {
        return redisManager.incr(sequence);
    }

    public RedisManager getRedisManager() {
        return redisManager;
    }

    public void setRedisManager(RedisManager redisManager) {
        this.redisManager = redisManager;
    }
}
