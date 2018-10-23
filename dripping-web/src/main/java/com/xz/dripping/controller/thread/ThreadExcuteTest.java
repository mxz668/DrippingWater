package com.xz.dripping.controller.thread;

import org.springframework.scheduling.concurrent.ScheduledExecutorFactoryBean;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by MABIAO on 2017/8/18.
 */
public class ThreadExcuteTest {

    public static void main(String args[]){
//        ScheduledExecutorService executor = null;
//        new ThreadExecutorFactory();

//        try{
//            throw new Exception();
//        }catch (Exception e){
//            System.out.println("12");
//        }
//        System.out.println("123");

        Set<String> set = new HashSet<>();
        set.add("1");
        set.remove("2");
    }
}
