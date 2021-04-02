package com.volatiledemo;

/**
 * 虽然Volatile解决了多线程间的可见性问题，但是对于原子性问题Volatile并不能保证。
 * 什么是原子性：
 * 原子性的意思是不可分割，完整性，也就是某个线程在执行某个具体业务时，中间不可以被加塞或者被分割，需要具体完整，要么同时成功，要么同时失败
 * ++操作包含三个阶段：
 * 1. 从内存读取i当前的值
 * 2. 加1
 * 3. 把修改后的值刷新到内存
 * 对于普通变量来说多线程下1，2之间被中断，其它线程修改了i的值，那原来已经在1，2之间被中断的线程的i的值就已经无效了，所以多线程是不安全的。
 * 另外对于普通变量来说，步骤1并不是每次都会从内存中读取，步骤3也并不会每次都保证会立即刷新到内存。
 * 所以这里有两个问题，可见性和原子性，viloate只能保证可见性，即步骤1每次都重新读，步骤3每次都立即刷新到主内存。但1，2之间仍然会被中断，多个线程交叉修改，所以仍然不安全。
 *
 * 具体解决办法可以采用加锁或者cas的方式
 */
public class VolatileDemo2 {

    private static volatile int num = 0;

    public static void main(String[] args) {

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int j = 0; j < 10000; j++) {
                    num++;
                }
            }).start();
        }


        //需要等待上面10个线程全部写完，再执行main线程
        while(Thread.activeCount() > 2) {
            //当前线程数大于2时，main线程让出
            Thread.yield();
        }

        //最终输出的结果不是预期的100000，且每次运行结果都不同
        System.out.println(num);
    }


}

