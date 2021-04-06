package com.synchronizeddemo;


/**
 * 在前面四个例子中，修饰的都是方法，下面介绍修饰代码块的使用。
 *
 * 一个常见的场景是,一个冗长的方法中，其实只有一小段代码需要访问共享资源，但是加了synchronized其他线程也要必须等待，
 * 有时候这种长时间等待是低效率且没有必要的，这时使用同步块，就只将这小段代码裹在synchronized  block，既能够实现同步访问，也能够减少同步引入的开销。 同步代码块须写在方法中。
 *
 * synchronized(this)锁住的是该类的实例对象
 */
public class SynchronizedDemo5 {

    public static void main(String[] args) {
        Sender5 sender = new Sender5();

        new Thread(() -> {
            sender.doLongHomeWork();
        }, "A").start();

        new Thread(() -> {
            sender.doLongHomeWork();
        }, "B").start();
    }

}



class Sender5 {
    public void doLongHomeWork() {

        //A,B线程不阻塞
        for (int i = 0; i < 100; i++) {
            System.out.println("nosynchronized threadName="
                    + Thread.currentThread().getName() + " i=" + (i + 1));
        }

        //A,B线程会阻塞在这里
        synchronized (this) {
            for (int i = 0; i < 100; i++) {
                System.out.println("synchronized threadName="
                        + Thread.currentThread().getName() + " i=" + (i + 1));
            }
        }
    }
}