package com.synchronizeddemo;

/**
 * 线程间之间的通信可以采用 wait、notify和notifyAll，这几个都是Object对象的属性
 * wait:        使持有该对象的线程把该对象的控制权交出去，然后处于等待状态
 * notify:      通知某个正在等待这个对象的控制权的线程可以继续运行
 * notifyAll:   会通知所有等待这个对象控制权的线程继续运行
 *
 * wait和notify必须要和synchronized一起使用，也就是wait与notify针对已经获取了锁的对象进行操作
 *
 * 案例： 两个线程顺序打出1到10，线程1打印奇数，线程2打印偶数
 */
public class SynchronizedDemo8 {

    public static void main(String[] args) {

        Object lock = new Object();
        Communication communication = new Communication(lock);

        new Thread(() -> {
            communication.printOdd();
        }).start();

        new Thread(() -> {
            communication.printEven();
        }).start();

    }


}

class Communication {

    Object lock;
    Communication(Object lock) {
        this.lock = lock;
    }

    public void printOdd() {
        synchronized (lock) {
            for (int i = 1; i < 10; i = i + 2) {
                System.out.println(i);
                lock.notify();
                try {
                    lock.wait();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void printEven() {
        synchronized (lock) {
            for (int i = 2; i <= 10; i = i + 2) {
                System.out.println(i);
                lock.notify();
                try {
                    lock.wait();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}