package com.xz.dripping.common.utils.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * ClassName: SpringRedisLock <br/>
 * Function: 使用spring封装的redis实现分布式锁. <br/>
 * Date: 2017年1月4日 下午3:52:07 <br/>
 *
 * @author Administrators
 * @since JDK 1.7
 */

public class SpringRedisLock implements Lock {

    /**
     * 日志器
     */
    private static final Logger log = LoggerFactory.getLogger(SpringRedisLock.class);

    /**
     * 锁定KEY值
     */
    private byte[] lockKey;

    /**
     * 默认KEY过期时间,单位:秒
     */
    private int expire = 10;

    /**
     * 加锁状态
     */
    private boolean isLocked = false;

    /**
     * 默认Try超时时间
     */
    public static int DEFAULT_TIME_OUT = 60;

    private RedisConnectionFactory redisConnectionFactory;

    public SpringRedisLock(RedisConnectionFactory redisConnectionFactory, byte[] lockKey) {
        this.redisConnectionFactory = redisConnectionFactory;
        this.lockKey = lockKey;
    }

    @Override
    public void lock() {

        try {
            tryLock(DEFAULT_TIME_OUT, null);
        } catch (Exception e) {
            log.error("尝试锁定KEY:" + new String(lockKey) + "失败");
            log.error(e.getMessage(), e);
        }
    }


    @Override
    public boolean tryLock() {
        try {
            return tryLock(0L, null);
        } catch (Exception e) {
            log.error("尝试锁定KEY:" + new String(lockKey) + "失败");
            log.error(e.getMessage(), e);
        }
        return false;
    }

    public boolean tryLock(long timeout) {
        try {
            return tryLock(timeout, null);
        } catch (Exception e) {
            log.error("尝试锁定KEY:" + new String(lockKey) + "失败");
            log.error(e.getMessage(), e);
        }
        return false;
    }

    @Override
    public boolean tryLock(long timeout, TimeUnit unit) throws InterruptedException {
        log.debug("尝试锁定KEY:" + new String(lockKey));
        RedisConnection redisConnection = null;
        try {

            redisConnection = redisConnectionFactory.getConnection();

            long nano = System.nanoTime();
            do {

                boolean bFlag = redisConnection.setNX(lockKey, lockKey);

                if (bFlag) {
                    isLocked = true;
                    redisConnection.expire(lockKey, this.expire);
                    log.info("成功将" + new String(lockKey) + "锁定" + expire + "秒");
                    return Boolean.TRUE;
                } else { // 存在锁

                    log.info("KEY值已经被锁定,等待");
                }
                if (timeout == 0) {
                    break;
                }
                Thread.sleep(300);
            } while ((System.nanoTime() - nano) < unit.toNanos(timeout));
            return Boolean.FALSE;
        } catch (Exception e) {
            log.error("锁定KEY值:" + new String(lockKey) + "失败");
            log.error(e.getMessage(), e);
        } finally {
            redisConnection.close();
        }
        return Boolean.FALSE;
    }

    @Override
    public void unlock() {
        if (isLocked == false)
            return;
        log.debug("尝试解锁KEY:" + new String(lockKey));
        RedisConnection redisConnection = null;
        try {
            redisConnection = redisConnectionFactory.getConnection();
            redisConnection.del(lockKey);
            isLocked = false;
        } catch (Exception e) {
            log.error("解锁KEY值:" + new String(lockKey) + "失败");
            log.error(e.getMessage(), e);
        } finally {

            redisConnection.close();
        }
    }

    @Override
    public Condition newCondition() {
        return null;
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    public byte[] getLockKey() {
        return lockKey;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public int getExpire() {
        return expire;
    }
}