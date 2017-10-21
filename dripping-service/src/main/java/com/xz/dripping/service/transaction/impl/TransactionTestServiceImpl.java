package com.xz.dripping.service.transaction.impl;

import com.xz.dripping.dal.dao.PoolDao;
import com.xz.dripping.dal.entity.Pool;
import com.xz.dripping.service.transaction.TransactionTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by MABIAO on 2017/10/21.
 */
@Service
public class TransactionTestServiceImpl implements TransactionTestService {

    @Autowired
    private PoolDao poolDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String updatePool(String poolId) throws Exception {
        return null;
    }

    /**
     * 测试同一事务 更新同一条数据
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String updatePoolBatch(List<Pool> poolList) throws Exception {

        for(Pool pool:poolList){
            Pool newPool = poolDao.selectByPrimaryKey(pool.getId());
            newPool.setTotalAmount(newPool.getTotalAmount().add(pool.getTotalAmount()));

            poolDao.updateByPrimaryKeySelective(newPool);
        }

        return null;
    }
}
