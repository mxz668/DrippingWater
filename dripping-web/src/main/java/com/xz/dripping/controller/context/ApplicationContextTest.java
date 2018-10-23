package com.xz.dripping.controller.context;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by MABIAO on 2017/9/26.
 */
public class ApplicationContextTest {


    public static void main(String args[]){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
//        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
//                new String[] { "classpath:applicationContext.xml" });
//        Object[] objs = applicationContext.
        System.out.println(applicationContext.getBean("velocityFactory"));
        System.out.println("123");

    }
}
