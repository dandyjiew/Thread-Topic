package com.reentrantlock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 在Java中，实现线程同步的传统方法是使用synchronized关键字(具体使用方法见synchronizeddemo包)
 * 但是synchronized的缺点也很明显，例如，一个线程只能获得一次锁，同步块不提供任何等待队列的机制，并且在一个线程退出后，任何线程都可以获得锁，这可能会导致其他线程在很长一段时间内缺乏资源。
 *
 * ReentrantLock 与 synchronized 的区别
 * 1. 两者都是独占锁，只允许线程互斥的访问临界区。但是是线上两者不同: synchronized加锁解锁的过程是隐式的, 用户不需要手动加锁和解锁。
 *    而ReentrantLock的加锁解锁由用户控制，且解锁一定要放在finally中，保证正确释放锁。
 *
 * 2. ReentrantLock和synchronized都是可重入的。synchronized因为可重入因此可以放在被递归执行的方法上,且不用担心线程最后能否正确释放锁；
 *    而ReentrantLock在重入时要却确保重复获取锁的次数必须和重复释放锁的次数一样，否则可能导致其他线程无法获得该锁。
 */
public class ReentrantLockDemo1 {

    private static final ReentrantLock lock = new ReentrantLock();
    public static void main(String[] args) {

        /*************** 基本使用 ***************/
        new Thread(() -> {
            doWorker();
        }, "A").start();

        new Thread(() -> {
            doWorker();
        }, "B").start();

        while(Thread.activeCount() > 2) {
            Thread.yield();
        }

        /*************** 可重入例子 ***************/
        for (int i = 0; i <= 3; i++) {
            System.out.println(Thread.currentThread().getName() + "第" + i + "获取了锁");
            lock.lock();
        }

        for (int i = 0; i <= 3; i++) {
            try {

            } finally {
                System.out.println(Thread.currentThread().getName() + "第" + i + "释放了锁");
                lock.unlock();
            }
        }
    }

    public static void doWorker() {
        //调用lock()方法将保持计数增加1，如果共享资源最初是空闲的，则将锁授予线程。
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "获取了锁");
            //模拟工作
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //调用unlock()方法将保持次数减少1。当这个计数达到零时，资源被释放。
            lock.unlock();
            System.out.println(Thread.currentThread().getName() + "释放了锁");
        }
    }
}