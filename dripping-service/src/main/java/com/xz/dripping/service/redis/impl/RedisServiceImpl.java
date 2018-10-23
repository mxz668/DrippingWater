package com.xz.dripping.service.redis.impl;

import com.xz.dripping.common.utils.JsonUtils;
import com.xz.dripping.service.redis.BaseRedisService;
import com.xz.dripping.service.redis.RedisService;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * Created by MABIAO on 2017/9/25.
 * redis 接口实现
 */
@Service
public class RedisServiceImpl  extends BaseRedisService<String, Serializable> implements RedisService {

    @Override
    public void get(final RedisModel redisModel) throws Exception{

        RedisModel result = redisTemplate.execute(new RedisCallback<RedisModel>() {
            public RedisModel doInRedis(RedisConnection connection)
                    throws DataAccessException {
                RedisSerializer<String> serializer = getRedisSerializer();
                byte[] key = serializer.serialize(redisModel.getId());
                byte[] value = connection.get(key);
                if (value == null) {
                    return null;
                }
                String objStr = serializer.deserialize(value);
                return JsonUtils.json2Object(objStr, RedisModel.class);
            }
        });

        System.out.println(result);
    }

    @Override
    public void add(final RedisModel redisModel) throws Exception{
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
            public Boolean doInRedis(RedisConnection connection)
                    throws DataAccessException {
                RedisSerializer<String> serializer = getRedisSerializer();
                byte[] key  = serializer.serialize(redisModel.getId());
                byte[] name = serializer.serialize(JsonUtils.object2Json(redisModel));
                boolean bl = connection.setNX(key, name);
                if(bl){//设置键的有效时间
                    connection.expire(key, 1800);
                }
                return bl;
            }
        });

        System.out.println(result);
    }
}
