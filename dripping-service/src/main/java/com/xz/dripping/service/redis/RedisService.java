package com.xz.dripping.service.redis;

import com.xz.dripping.service.redis.impl.RedisModel;

/**
 * Created by MABIAO on 2017/9/25.
 * redis 接口
 */
public interface RedisService {

    public void get(final RedisModel redisModel) throws Exception;

    void add(final RedisModel redisModel) throws Exception;
}
