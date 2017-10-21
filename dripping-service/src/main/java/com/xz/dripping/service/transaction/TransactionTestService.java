package com.xz.dripping.service.transaction;


import com.xz.dripping.dal.entity.Pool;

import java.util.List;

/**
 * Created by MABIAO on 2017/10/21.
 */
public interface TransactionTestService {

    public String updatePool(String poolId) throws Exception;

    public String updatePoolBatch(List<Pool> poolList) throws Exception;
}
