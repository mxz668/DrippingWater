package com.xz.dripping.controller.array;

/**
 * Created by MABIAO on 2018/3/23.
 */
public class StaticClass {

    public static void main(String args[]){
//        System.out.println(Static1.in);
//        System.out.println(Static2.in);
//
//        Static1.in += 1;
//        Static2.in += 1;
//
//        System.out.println(Static1.in);
//        System.out.println(Static2.in);

        Static1 s1 = new Static1(){
            {Static1("");}
        };
    }
}


class Static1{
    public static int in = 0;

    public Static1(){
        System.out.println("Static1无参构造被调用了");
    }

    protected void Static1(String str){
        System.out.println("Static1有参构造被调用了");
    }
}


class Static2{
    public static int in = 1;

    public Static2(){}
}