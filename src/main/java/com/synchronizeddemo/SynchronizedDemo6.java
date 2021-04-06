package com.synchronizeddemo;

/**
 * 除了Demo5用this当前对象(this)作为锁之外，还可以用将任意对象作为对象监视器
 * 格式: synchronized(非this对象x){......}
 * 注意:
 *  1. 当多个线程持有的对象监听器为同一个对象时，依旧是同步的，同一时间只有一个可以访问，
 *  2. 但是对象不同，执行就是异步的。
 * 好处:
 * 因为如果一个类有很多synchronized方法或synchronized（this）代码块，还是会影响效率，这时用synchronized(非this)同步代码块就不会和其它锁this同步方法争抢this锁
 */
public class SynchronizedDemo6 {

    public static void main(String[] args) throws InterruptedException {

        /************************************************************/
        Sender61 sender1 = new Sender61();

        new Thread(() -> {
            sender1.run1();
        }, "A").start();

        new Thread(() -> {
            sender1.run2();
        }, "B").start();

        new Thread(() -> {
            sender1.run3();
        }, "C").start();
        /************************************************************/

        //需要等待线程全部跑完，再执行main线程
        while(Thread.activeCount() > 2) {
            //当前线程数大于2时，main线程让出
            Thread.yield();
        }

        /**
         * 下面两个线程会异步执行，不会争夺同把锁
         */
        Sender62 sender2 = new Sender62();

        new Thread(() -> {
            sender2.run1();
        }, "D").start();

        new Thread(() -> {
            sender2.run2();
        }, "E").start();
    }
}

/**
 * 这里两个同步代码快使用的监视器都是this，会一起争夺锁.
 * run1 run2 run3 会一起争夺锁
 */
class Sender61 {
    public void run1() {
        synchronized (this) {
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

    public synchronized void run3() {
        for (int i = 0; i < 100; i++) {
            System.out.println(Thread.currentThread().getName() + "运行了" + i + "次");
        }
    }
}

class Sender62 {
    private Object object = new Object();
    public void run1() {
        synchronized (object) {
            for (int i = 0; i < 100; i++) {
                System.out.println(Thread.currentThread().getName() + "运行了" + i + "次");
            }
        }
    }

    public synchronized void run2() {
        for (int i = 0; i < 100; i++) {
            System.out.println(Thread.currentThread().getName() + "运行了" + i + "次");
        }
    }
}
