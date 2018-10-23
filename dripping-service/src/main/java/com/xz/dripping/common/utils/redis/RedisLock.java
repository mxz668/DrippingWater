package com.xz.dripping.common.utils.redis;

import com.xz.dripping.common.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * ClassName: RedisLock <br/>
 * Function: redis lock. <br/>
 * Date: 2016年11月5日 上午9:43:49 <br/>
 *
 * @author Administrators
 * @since JDK 1.7
 */
public class RedisLock implements Lock {

    /**
     * 日志器
     */
    private static final Logger log = LoggerFactory.getLogger(RedisLock.class);

    /**
     * Redis管理器
     */
    private RedisManager redisManager;

    /**
     * 锁定KEY值
     */
    private String lockKey;

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

    public RedisLock() {
    }

    public RedisLock(RedisManager redisManager, String lockKey) {
        this.redisManager = redisManager;
        this.lockKey = lockKey;
    }

    @Override
    public void lock() {
        try {
            tryLock(DEFAULT_TIME_OUT, null);
        } catch (Exception e) {
            log.error("尝试锁定KEY:{}失败", lockKey);
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        try {
            return tryLock(0L, null);
        } catch (Exception e) {
            log.error("尝试锁定KEY:{}失败", lockKey);
            log.error(e.getMessage(), e);
        }
        return false;
    }

    public boolean tryLockNotExpire(String lockKey) {
        try {
            this.lockKey = lockKey;
            this.expire = -1;
            return tryLockWithTime(0L, null);
        } catch (Exception e) {
            log.error("尝试锁定KEY:{}失败", lockKey);
            log.error(e.getMessage(), e);
        }
        return false;
    }

    public boolean tryLockNotExpire(String lockKey, long timeout) {
        try {
            this.lockKey = lockKey;
            this.expire = -1;
            return tryLockWithTime(timeout, null);
        } catch (Exception e) {
            log.error("尝试锁定KEY:{}失败", lockKey);
            log.error(e.getMessage(), e);
        }
        return false;
    }

    public void unlock(String lockKey) {
        this.lockKey = lockKey;
        if (isLocked == false)
            return;
        log.debug("尝试解锁KEY:{}", lockKey);
        Jedis jedis = null;
        try {
            jedis = redisManager.getJedis();
            jedis.del(lockKey);
            isLocked = false;
        } catch (Exception e) {
            log.error("解锁KEY值{}失败", lockKey);
            log.error(e.getMessage(), e);
        } finally {
            redisManager.returnResource(jedis);
        }
    }

    public boolean tryLock(long timeout) {
        try {
            return tryLock(timeout, null);
        } catch (Exception e) {
            log.error("尝试锁定KEY:{}失败", lockKey);
            log.error(e.getMessage(), e);
        }
        return false;
    }

    public boolean tryLockWithTime(long timeout, TimeUnit unit) throws InterruptedException {
        log.debug("尝试锁定KEY:{}", lockKey);
        Jedis jedis = null;
        try {
            jedis = redisManager.getJedis();
            long nano = System.nanoTime();
            do {
                Long i = jedis.setnx(lockKey, DateUtils.format(DateUtils.now(), DateUtils.DEFAULT_TIMESTAMP_FORMAT));

                if (i == 1) {
                    isLocked = true;
                    jedis.expire(lockKey, this.expire);
                    log.debug("成功将{}锁定{}秒", lockKey, expire);
                    return Boolean.TRUE;
                } else { // 存在锁
                    if (timeout == 0) {
                        log.debug("KEY值{}已经被锁定", lockKey);
                        break;
                    } else {
                        log.debug("KEY值{}已经被锁定,等待", lockKey);
                    }
                }
                Thread.sleep(300);
            } while ((System.nanoTime() - nano) < unit.toNanos(timeout));
            return Boolean.FALSE;
        } catch (Exception e) {
            log.error("锁定KEY值{}失败", lockKey);
            log.error(e.getMessage(), e);
        } finally {
            redisManager.returnResource(jedis);
        }
        return Boolean.FALSE;
    }

    @Override
    public boolean tryLock(long timeout, TimeUnit unit) throws InterruptedException {
        log.debug("尝试锁定KEY:{}", lockKey);
        Jedis jedis = null;
        try {
            jedis = redisManager.getJedis();
            long nano = System.nanoTime();
            do {
                Long i = jedis.setnx(lockKey, lockKey);

                if (i == 1) {
                    isLocked = true;
                    jedis.expire(lockKey, this.expire);
                    log.debug("成功将{}锁定{}秒", lockKey, expire);
                    return Boolean.TRUE;
                } else { // 存在锁
                    if (timeout == 0) {
                        log.debug("KEY值{}已经被锁定", lockKey);
                        break;
                    } else {
                        log.debug("KEY值{}已经被锁定,等待", lockKey);
                    }
                }
                Thread.sleep(300);
            } while ((System.nanoTime() - nano) < unit.toNanos(timeout));
            return Boolean.FALSE;
        } catch (Exception e) {
            log.error("锁定KEY值{}失败", lockKey);
            log.error(e.getMessage(), e);
        } finally {
            redisManager.returnResource(jedis);
        }
        return Boolean.FALSE;
    }

    /**
     * Acquire lock.
     *
     * @param jedis
     * @return true if lock is acquired, false acquire timeouted
     * @throws InterruptedException in case of thread interruption
     */
    /*public synchronized boolean acquire(Jedis jedis) throws InterruptedException {
        int timeout = timeoutMsecs;
        while (timeout >= 0) {
            long expires = System.currentTimeMillis() + expireMsecs + 1;
            String expiresStr = String.valueOf(expires); //锁到期时间

            if (jedis.setnx(lockKey, expiresStr) == 1) {
                // lock acquired
                locked = true;
                return true;
            }

            String currentValueStr = jedis.get(lockKey); //redis里的时间
            if (currentValueStr != null && Long.parseLong(currentValueStr) < System.currentTimeMillis()) {
                //判断是否为空，不为空的情况下，如果被其他线程设置了值，则第二个条件判断是过不去的
                // lock is expired

                String oldValueStr = jedis.getSet(lockKey, expiresStr);
                //获取上一个锁到期时间，并设置现在的锁到期时间，
                //只有一个线程才能获取上一个线上的设置时间，因为jedis.getSet是同步的
                if (oldValueStr != null && oldValueStr.equals(currentValueStr)) {
                    //如过这个时候，多个线程恰好都到了这里，但是只有一个线程的设置值和当前值相同，他才有权利获取锁
                    // lock acquired
                    locked = true;
                    return true;
                }
            }
            timeout -= 100;
            Thread.sleep(100);
        }
        return false;
    }*/
    @Override
    public void unlock() {
        if (isLocked == false)
            return;
        log.debug("尝试解锁KEY:{}", lockKey);
        Jedis jedis = null;
        try {
            jedis = redisManager.getJedis();
            jedis.del(lockKey);
            isLocked = false;
        } catch (Exception e) {
            log.error("解锁KEY值{}失败", lockKey);
            log.error(e.getMessage(), e);
        } finally {
            redisManager.returnResource(jedis);
        }
    }

    @Override
    public Condition newCondition() {
        return null;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public String getLockKey() {
        return lockKey;
    }

    public int getExpire() {
        return expire;
    }

    public RedisManager getRedisManager() {
        return redisManager;
    }

    public void setRedisManager(RedisManager redisManager) {
        this.redisManager = redisManager;
    }
}