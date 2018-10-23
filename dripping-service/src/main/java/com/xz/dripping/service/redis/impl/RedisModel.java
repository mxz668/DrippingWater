package com.xz.dripping.service.redis.impl;

import java.io.Serializable;

/**
 * Created by MABIAO on 2017/9/25.
 */
public class RedisModel implements Serializable{

    private String id;

    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
