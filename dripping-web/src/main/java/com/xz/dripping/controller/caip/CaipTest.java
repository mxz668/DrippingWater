package com.xz.dripping.controller.caip;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by MABIAO on 2017/9/29.
 */
public class CaipTest {

    public static void main(String args[]) {
        Random rd = new Random();//随机生成数
        List<Integer> red = new ArrayList<Integer>(6);
        red.add(0);
        red.add(0);
        red.add(0);
        red.add(0);
        red.add(0);
        red.add(0);
        for (int i = 0; i < 6; i++) {
            int rednum = rd.nextInt(33) + 1;
            boolean flag = true;
            for (int j = 0; j <= i; j++) {
                if (rednum == red.get(j)) {
                    i--;
                    //System.out.print("输出的是："+red[j]+ "\t");
                    flag = false;
                    break;
                }
            }
            if (flag) {
                red.set(i, rednum);
            }
        }
        Collections.sort(red);
        System.out.println("输出的红球是：");
        for (int i = 0; i < red.size(); i++) {
            System.out.println(red.get(i) + "\t");
        }
        int bluenum = rd.nextInt(16) + 1;
        System.out.println("输出的蓝色球是：" + bluenum);
    }
}
