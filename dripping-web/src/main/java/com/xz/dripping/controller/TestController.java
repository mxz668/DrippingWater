package com.xz.dripping.controller;

import com.xz.dripping.common.utils.DateUtils;
import com.xz.dripping.facade.service.TestFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by MABIAO on 2017/6/2 0002.
 */
@RestController
@RequestMapping("testController")
public class TestController {

    Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private TestFacade testFacade;

    @RequestMapping(value = "saveTest",method = RequestMethod.POST)
    public void saveTest(){
        try {
            testFacade.saveTest();
        }catch (Exception e){
            logger.info("创建失败");
        }
    }

    public static void main(String args[]){
//        String yearMonth = DateUtils.format(DateUtils.now(), DateUtils.DATE_FORMAT_YYMMDD);
//        Date d = DateUtils.parse("20170920", DateUtils.DATE_FORMAT_YYMMDD);\
//        String string = "AXD";
//        System.out.println(String.format("%0" + 5 + "d",2000000l));

        BigDecimal b = new BigDecimal("127867564.12");
        System.out.println(b.setScale(0,BigDecimal.ROUND_DOWN));
    }
}
