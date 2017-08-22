package com.xz.dripping.controller.thread;

import java.util.Timer;

/**
 * Created by MABIAO on 2017/8/21.
 */
public class TestThreadPool {
    public static void main(String[] args) {
//        double num = Math.random()*10000;
//        num = Math.round(num);
//        System.out.println(num);

        // 创建3个线程的线程池
        ThreadPool t = ThreadPool.getThreadPool(5);
//        t.execute(new Runnable[] { new Task(), new Task(), new Task() ,new Task(), new Task(), new Task()});
//        t.execute(new Runnable[] { new Task(), new Task(), new Task() });
//        t.execute(new Runnable[] { new Task(), new Task(), new Task() });
        for (int i=1;i <= 40;i++){
            t.execute(new Task());
        }
        long start = System.currentTimeMillis();
        System.out.println("start -->" + start);
        System.out.println(t);
        t.destroy();// 所有线程都执行完成才destory
        System.out.println(t);
        long end = System.currentTimeMillis();
        System.out.println("end -->" + end);
        System.out.println(end-start);
    }

    // 任务类
    static class Task implements Runnable {
        private static volatile int i = 1;

        @Override
        public void run() {// 执行任务
            double num = Math.random()*1000;
            num = Math.round(num);
            Long n = new Double(num).longValue();
            try {
                Thread.sleep(n);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("任务 " + (i++) + " 完成");
        }
    }
}