package com.xz.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by MABIAO on 2017/6/2 0002.
 */
@RestController
@RequestMapping("testController")
public class TestController {

    Logger logger = LoggerFactory.getLogger(TestController.class);

    @RequestMapping(value = "test",method = RequestMethod.POST)
    public void test(){
        int i = 1;
        System.out.println(i);
        logger.info("hah");
    }
}
