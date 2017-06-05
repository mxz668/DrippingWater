package com.xz.dripping.dal.dao;

import com.xz.dripping.dal.entity.Test;

public interface TestDao {
    int insert(Test record);

    int insertSelective(Test record);
}