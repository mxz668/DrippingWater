package com.xz.dripping.controller.thread;

import org.springframework.scheduling.concurrent.ScheduledExecutorFactoryBean;

import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by MABIAO on 2017/8/18.
 */
public class ThreadExcuteTest {

    public static void main(String args[]){
        ScheduledExecutorService executor = null;
        new ThreadExecutorFactory();
    }
}
