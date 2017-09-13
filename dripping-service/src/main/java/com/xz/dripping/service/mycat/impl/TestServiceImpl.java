package com.xz.dripping.service.mycat.impl;

import com.xz.dripping.dal.dao.TestDao;
import com.xz.dripping.dal.entity.Test;
import com.xz.dripping.service.mycat.TestService;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * Created by MABIAO on 2017/6/5 0005.
 */
@Service
public class TestServiceImpl implements TestService {

    private static Logger logger = LoggerFactory.getLogger(TestServiceImpl.class);

    @Autowired
    private TestDao testDao;

    private String zkConfig = "127.0.0.1:2181";

    @Override
    public String saveTest(Test test) throws Exception {
        CuratorFramework client = CuratorFrameworkFactory.newClient(zkConfig, new ExponentialBackoffRetry(1000, 3));
        client.start();

        try {
            InterProcessMutex lock = new InterProcessMutex(client, "/TEST-001");

            if (lock.acquire(3, TimeUnit.SECONDS)) {
                try {
//                    testDao.insertSelective(test);
                } finally {
                    lock.release();
                }
            }
        } catch (Exception e) {
            logger.error("异常:" + e.getMessage());
        }finally {
            CloseableUtils.closeQuietly(client);
        }
        return "";
    }
}
