package com.xz.dripping.controller.transaction;

import com.xz.dripping.dal.entity.Pool;
import com.xz.dripping.service.transaction.TransactionTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MABIAO on 2017/10/21.
 */
@RestController
@RequestMapping("transactionService")
public class TransactionTestController {

    @Autowired
    private TransactionTestService transactionTestService;

    @RequestMapping(value = "updatePool",method = RequestMethod.POST)
    public String updatePool(String poolId){

        return "";
    }

    /**
     * 测试同一事务 更新同一条数据
     * @return
     */
    @RequestMapping(value = "updatePoolBatch",method = RequestMethod.POST)
    public String updatePoolBatch(){
        List<Pool> pools = new ArrayList<>();
        Pool pool = new Pool();
        pool.setId(1l);
        pool.setTotalAmount(new BigDecimal("20"));

        Pool pool2 = new Pool();
        pool2.setId(1l);
        pool2.setTotalAmount(new BigDecimal("30"));

        pools.add(pool);
        pools.add(pool2);

        try {
            transactionTestService.updatePoolBatch(pools);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }
}
