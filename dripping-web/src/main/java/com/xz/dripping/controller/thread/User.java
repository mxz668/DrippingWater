package com.xz.dripping.controller.thread;

/**
 * Created by MABIAO on 2017/8/30.
 */
public class User implements Runnable {
    private static Account account = new Account();
    private final int id;

    User(int i) {
        id = i;
    }

    @Override
    public void run() {
        int tempMoney = 100;
        account.load("ren", tempMoney);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        account.save("ren", 100);
        System.out.println("线程" + id + "完毕========================================================");
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            new Thread(new User(i)).start();
        }
    }
}
