package com.xz.dripping.facade.impl;

import com.xz.dripping.facade.service.TestFacade;
import com.xz.dripping.service.mycat.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by MABIAO on 2017/6/5 0005.
 */
@Service
public class TestFacadeImpl implements TestFacade {

    @Autowired
    private TestService testService;

    public String saveTest(){
        try {
            testService.saveTest(null);
        }catch (Exception e){

        }

        return "";
    }
}
