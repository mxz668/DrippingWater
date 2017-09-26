package com.xz.dripping.controller.redis;

import com.xz.dripping.service.redis.BaseRedisService;
import com.xz.dripping.service.redis.RedisService;
import com.xz.dripping.service.redis.impl.RedisModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by MABIAO on 2017/9/4.
 */
@RestController
@RequestMapping(value = "/redisService")
public class RedisController extends BaseRedisService<String, RedisModel> {

    @Autowired
    private RedisService redisService;

    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public void get(@RequestBody RedisModel redisModel) throws Exception{
        redisService.get(redisModel);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public void add(@RequestBody RedisModel redisModel) throws Exception{
        redisService.add(redisModel);
    }
}
