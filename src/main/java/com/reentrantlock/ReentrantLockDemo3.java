package com.reentrantlock;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * reentrantlock支持可响应中断，当一个线程获取不到锁的时候，不会傻傻的一直等下去，ReentrantLock会给予一个中断回应
 * 而synchronized不支持，阻塞在锁上的线程除非获得锁否则将一直等待下去，也就是说这种无限等待获取锁的行为无法被中断
 *
 * 案例：死锁。A占有A锁，想获取B锁，B占有B锁，想获取A锁，且A、B都不释放资源，那么就会陷入死锁
 */
public class ReentrantLockDemo3 {

    private static final Lock lock1 = new ReentrantLock();
    private static final Lock lock2 = new ReentrantLock();
    public static void main(String[] args) {

        //先获取锁1，再获取锁2
        Thread thread1 = new Thread(new Worker(lock1, lock2), "A");
        //先获取锁2，再获取锁1
        Thread thread2 = new Thread(new Worker(lock2, lock1), "B");
        thread1.start();
        thread2.start();
        //没有这句代码会一直陷入死锁状态，由线程1阻断释放资源后，线程2就可以获取资源了
        thread1.interrupt();
    }


}

class Worker implements Runnable {
    Lock firstLock;
    Lock secondLock;
    public Worker(Lock firstLock, Lock secondLock) {
        this.firstLock = firstLock;
        this.secondLock = secondLock;
    }

    @Override
    public void run() {
        try {
            firstLock.lock();
            System.out.println(Thread.currentThread().getName() + " 成功获取到了锁1");
            Thread.sleep(100);
            secondLock.lock();
            System.out.println(Thread.currentThread().getName() + " 成功获取到了锁2");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName() + " 释放了资源");
            firstLock.unlock();
            secondLock.unlock();
        }
    }
}
