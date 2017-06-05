package com.xz.dripping.service.impl;

import com.xz.dripping.dal.dao.TestDao;
import com.xz.dripping.dal.entity.Test;
import com.xz.dripping.service.TestService;
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
    public String saveTest() throws Exception {
        Test test = new Test();
        test.setId(1);
        test.setName("123");
        testDao.insertSelective(test);
        return "";
    }
}
