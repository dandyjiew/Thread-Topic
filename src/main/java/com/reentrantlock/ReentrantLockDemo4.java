package com.reentrantlock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock有个tryLock()方法，该方法尝试立即锁定实例，如果锁定成功则返回true，如果锁定已被其他线程锁定，则返回false
 * tryLock()方法永远不会阻塞。
 *
 * tryLock(long timeout, TimeUnit timeUnit)的工作方式与tryLock()方法类似，不同的是它在放弃锁定之前等待给定的超时。
 * 案例：用tryLock方法解决死锁问题
 *
 */
public class ReentrantLockDemo4 {

    private static final Lock lock1 = new ReentrantLock();
    private static final Lock lock2 = new ReentrantLock();
    public static void main(String[] args) {

        //先获取锁1，再获取锁2
        Thread thread1 = new Thread(new Worker1(lock1, lock2), "A");
        //先获取锁2，再获取锁1
        Thread thread2 = new Thread(new Worker1(lock2, lock1), "B");
        thread1.start();
        thread2.start();
    }

}

class Worker1 implements Runnable {
    Lock firstLock;
    Lock secondLock;
    public Worker1(Lock firstLock, Lock secondLock) {
        this.firstLock = firstLock;
        this.secondLock = secondLock;
    }

    @Override
    public void run() {
        try {
            //也可以设置tryLock的超时等待时间tryLock(long timeout,TimeUnit unit)，也就是说一个线程在指定的时间内没有获取锁，那就会返回false，就可以再去做其他事了。
            //当获取到锁后，休眠，保证两个线程都能获取各自的第一把锁
            if (firstLock.tryLock()) {
                TimeUnit.MILLISECONDS.sleep(100);
            }

            //获取不到锁，放弃争夺
            if (!secondLock.tryLock()) {
                TimeUnit.MILLISECONDS.sleep(10);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            firstLock.unlock();
            secondLock.unlock();
            System.out.println(Thread.currentThread().getName() + "正常结束");
        }
    }
}