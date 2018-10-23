package com.xz.dripping.thread;

/**
 * Created by Administrator on 2017/8/7.
 */
public class SynchronizedObject {

    public void methodA(){
        try{
            System.out.println("begin name="+Thread.currentThread().getName());
            Thread.sleep(5000);
            System.out.println("end");
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public static void main(String args[]){
        SynchronizedObject object = new SynchronizedObject();
        SynchronizedThreadA a = new SynchronizedThreadA(object);
        a.setName("A");
        SynchronizedThreadB b = new SynchronizedThreadB(object);
        b.setName("B");
        b.start();
        a.start();
    }
}
