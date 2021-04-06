package com.synchronizeddemo;

/**
 * 用class对象作为锁，锁住的是整个类对象
 */
public class SynchronizedDemo7 {

    public static void main(String[] args) {

        Sender7 sender1 = new Sender7();
        Sender7 sender2 = new Sender7();

        new Thread(() -> {
            sender1.run1();
        }, "A").start();

        new Thread(() -> {
            sender1.run2();
        }, "B").start();

        new Thread(() -> {
            sender2.run1();
        }, "C").start();
    }
}

class Sender7 {
    public void run1() {
        synchronized (Sender7.class) {
            for (int i = 0; i < 100; i++) {
                System.out.println(Thread.currentThread().getName() + "运行了" + i + "次");
            }
        }
    }

    public void run2() {
        synchronized (this) {
            for (int i = 0; i < 100; i++) {
                System.out.println(Thread.currentThread().getName() + "运行了" + i + "次");
            }
        }
    }
}