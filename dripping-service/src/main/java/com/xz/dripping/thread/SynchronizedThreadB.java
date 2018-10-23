package com.xz.dripping.thread;

/**
 * Created by Administrator on 2017/8/7.
 */
public class SynchronizedThreadB extends Thread {

    private SynchronizedObject object;

    public SynchronizedThreadB(SynchronizedObject object){
        super();
        this.object = object;
    }

    @Override
    public void run(){
        super.run();
        object.methodA();
    }
}
