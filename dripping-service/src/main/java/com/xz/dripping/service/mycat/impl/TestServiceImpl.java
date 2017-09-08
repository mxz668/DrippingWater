package com.xz.dripping.service.mycat.impl;

import com.xz.dripping.dal.dao.TestDao;
import com.xz.dripping.dal.entity.Test;
import com.xz.dripping.service.mycat.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by MABIAO on 2017/6/5 0005.
 */
@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private TestDao testDao;

    @Override
    public String saveTest(Test test) throws Exception {
        testDao.insertSelective(test);
        return "";
    }
}
