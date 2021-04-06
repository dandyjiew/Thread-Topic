package com.reentrantlock;


import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock支持公平锁和非公平锁，synchronized仅支持非公平锁
 *
 * 公平锁：获取不到锁的时候，会自动加入队列，等待线程释放后，队列的第一个线程获取锁（越早加入的，越早获得锁）
 * 非公平锁：获取不到锁的时候，会自动加入队列，等待线程释放锁后所有等待的线程同时去竞争（会产生饥饿线程的可能）
 */
public class ReentrantLockDemo2 {

    //ReentrantLock默认采用的是非公平锁，构造参数中传入true，即为公平锁。可以删除true，查看一下非公平锁的结果，做对比
    private static final ReentrantLock lock = new ReentrantLock(true);
    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                worker();
            }, "第" + i + "个线程").start();
        }
    }

    public static void worker() {
        System.out.println(Thread.currentThread().getName() + " 在等待");
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " 获取了锁");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
            System.out.println(Thread.currentThread().getName() + " 释放了锁");
        }
    }
}
