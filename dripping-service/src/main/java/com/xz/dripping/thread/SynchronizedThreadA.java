package com.xz.dripping.thread;

/**
 * Created by Administrator on 2017/8/7.
 */
public class SynchronizedThreadA extends Thread {

    private SynchronizedObject object;

    public SynchronizedThreadA(SynchronizedObject object){
        super();
        this.object = object;
    }

    @Override
    public void run(){
        super.run();
        object.methodA();
    }
}
