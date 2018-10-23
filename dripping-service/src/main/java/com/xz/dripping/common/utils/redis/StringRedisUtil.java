package com.xz.dripping.common.utils.redis;

import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * ClassName: RedisLock <br/>
 * Function: redis lock. <br/>
 * Date: 2016年11月5日 上午9:43:49 <br/>
 *
 * @author Administrators
 * @since JDK 1.7
 */
public class StringRedisUtil {

    private StringRedisTemplate stringRedisTemplate;

    public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    /**
     * 保存数据/更新数据 并设置超时时间<br/>
     * 如果value没有实现Serializable接口,则会保存不成功(内部已经处理了异常)
     *
     * @param key     传入相同的key会覆盖之前同名key的数据的内容及设置
     * @param value   缓存的值，object类型
     * @param timeout 超时时间，单位为秒，如果传值<=0，则为永不过期
     */
    public void saveOrUpdate(String key, String value, long timeout) {

        if (timeout <= 0) {
            set(key, value);
        } else {
            setEx(key, value, timeout, TimeUnit.SECONDS);
        }
    }

    /**
     * 保存数据/更新数据 并设置超时时间<br/>
     * 如果value没有实现Serializable接口,则会保存不成功(内部已经处理了异常)
     *
     * @param key   传入相同的key会覆盖之前同名key的数据的内容及设置
     * @param value 缓存的值，object类型
     */
    public void set(String key, String value) {
        ValueOperations<String, String> valueOper = stringRedisTemplate.opsForValue();
        valueOper.set(key, value);
    }

    /**
     * @param key
     * @param value
     * @param timeout
     * @param timeUnit
     */
    public void setEx(String key, String value, long timeout, TimeUnit timeUnit) {
        ValueOperations<String, String> valueOper = stringRedisTemplate.opsForValue();
        valueOper.set(key, value, timeout, timeUnit);
    }

    /**
     * @param key
     * @param value
     * @param timeout
     */
    public void setEx(String key, String value, long timeout) {
        ValueOperations<String, String> valueOper = stringRedisTemplate.opsForValue();
        valueOper.set(key, value, timeout);
    }

    /**
     * 根据key查询得到对应的值
     *
     * @param key
     * @return
     */
    public String get(String key) {
        ValueOperations<String, String> valueOper = stringRedisTemplate.opsForValue();
        return valueOper.get(key);
    }

    /**
     * 检查key和对应的值在缓存中是否存在
     *
     * @param key
     * @return 存在返回 true，否则返回false
     */
    public boolean exist(String key) {
        ValueOperations<String, String> valueOper = stringRedisTemplate.opsForValue();
        boolean exist = valueOper.getOperations().hasKey(key);
        return exist;
    }

    /**
     * 根据key删除key及对应的值
     *
     * @param id
     */
    public void delete(String id) {
        ValueOperations<String, String> valueOper = stringRedisTemplate.opsForValue();
        RedisOperations<String, String> redisOperations = valueOper.getOperations();
        redisOperations.delete(id);
    }

    /**
     * 列举keys
     *
     * @param pattern 匹配符
     */
    public Set<String> keys(String pattern) {
        return stringRedisTemplate.keys(pattern);
    }
}