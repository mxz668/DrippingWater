package com.xz.dripping.controller.mycat;

import com.xz.dripping.dal.entity.Test;
import com.xz.dripping.facade.dto.req.mycat.TestRequest;
import com.xz.dripping.service.mycat.TestService;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by MABIAO on 2017/9/4.
 */
@RestController
@RequestMapping(value = "/myCatService")
public class MyCatController {

    @Autowired
    private TestService testService;

    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public void test(@RequestBody TestRequest req) throws Exception{
        Test test = new Test();
        PropertyUtils.copyProperties(test,req);

        SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);
        long id = idWorker.nextId();
        test.setId(id);

        testService.saveTest(test);
    }
}
