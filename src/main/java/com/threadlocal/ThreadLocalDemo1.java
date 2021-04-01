package com.threadlocal;


/**
 * ThreadLocal相当于ThreadLocalMap的外壳，主要使用的还是ThreadLocalMap
 * Map的key是线程对象，value是要保存的对象
 * 操作：当我们想从线程中使用这个值时，我们只需要调用get()或set()方法。
 * 用途：允许我们存储只有特定线程才能访问的数据，不存在多线程间的共享问题，是线程隔离的
 */
public class ThreadLocalDemo1 {
    public static final ThreadLocal<Integer> threadLocal = new ThreadLocal<>();

    public static void main(String[] args) {
        threadLocal.set(10);
        System.out.println("主线程的获得的value: " + threadLocal.get());
        new Thread(() -> {
            threadLocal.set(20);
            System.out.println("新建线程获得的value: " + threadLocal.get());
        }).start();
        //可以发现主线程获得的值还是前面开始设定的10不变，与新建的线程是隔离开来的
        System.out.println("主线程的获得的value: " + threadLocal.get());
    }
}
