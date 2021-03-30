package com.createthread;


/**
 * 使用匿名类的方式，一是重写Thread的run()方法，二是传入Runnable的匿名类，三是使用lambda方式，现在一般使用第三种（java8+），简单快捷。
 * 参考：https://www.toutiao.com/a6745756522250240526/
 */
public class Method4 {

    public static void main(String[] args) {

        //Thread匿名类，重写Thread的run()方法
        new Thread() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " is running");
            }
        }.start();

        //Runnable匿名类，实现其run()方法
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " is running");
            }
        }).start();

        // 同上，使用lambda表达式函数式编程
        new Thread(()->{
            System.out.println(Thread.currentThread().getName() + " is running");
        }).start();
    }
}
