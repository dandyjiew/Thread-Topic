package com.reentrantlock;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用synchronized结合Object上的wait和notify方法可以实现线程间的等待通知机制。
 * Condition接口同样可以实现这个功能。而且相比前者使用起来更清晰也更简单。
 * 一个Lock对象里可以创建多个Condition（即对象监视器）实例，线程对象可以注册在指定的Condition中，从而可以有选择的进行线程通知，在调度线程上更加灵活
 * 案例：三个线程，顺序交替打印
 *
 * condition.await方法会释放当前占用的锁，调用signal()方法后系统会从condition.await()等待队列中唤醒一个线程。当线程被唤醒后，它就会尝试重新获得与之绑定的重入锁，一旦获取成功将继续执行。
 * 所以调用signal()方法后一定要释放当前占用的锁，这样被唤醒的线程才能有获得锁的机会，才能继续执行。
 */
public class ReentrantLockDemo5 {

    private static final Lock lock = new ReentrantLock();
    private static final Condition conditionA = lock.newCondition();
    private static final Condition conditionB = lock.newCondition();
    private static final Condition conditionC = lock.newCondition();

    public static void main(String[] args) {

        PrintOne printOne = new PrintOne(lock, conditionA, conditionB);
        PrintTwo printTwo = new PrintTwo(lock, conditionB, conditionC);
        PrintThree printThree = new PrintThree(lock, conditionA, conditionC);

        new Thread(() -> {
            printOne.run();
        }).start();

        try {
            Thread.sleep(10);
        } catch (Exception e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            printTwo.run();
        }).start();

        try {
            Thread.sleep(10);
        } catch (Exception e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            printThree.run();
        }).start();

    }
}

class PrintOne {

    private Lock lock;
    private Condition conditionA;
    private Condition conditionB;

    PrintOne(Lock lock, Condition conditionA, Condition conditionB) {
        this.lock = lock;
        this.conditionA = conditionA;
        this.conditionB = conditionB;
    }

    public void run() {
        lock.lock();
        try {
            for (int i = 0; i < 10; i++) {
                System.out.println(1);
                conditionB.signal();
                conditionA.await();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }
}

class PrintTwo {

    private Lock lock;
    private Condition conditionB;
    private Condition conditionC;

    PrintTwo(Lock lock, Condition conditionB, Condition conditionC) {
        this.lock = lock;
        this.conditionB = conditionB;
        this.conditionC = conditionC;
    }

    public void run() {
        lock.lock();
        try {
            for (int i = 0; i < 10; i++) {
                System.out.println(2);
                conditionC.signal();
                conditionB.await();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }
}

class PrintThree {

    private Lock lock;
    private Condition conditionA;
    private Condition conditionC;

    PrintThree(Lock lock, Condition conditionA, Condition conditionC) {
        this.lock = lock;
        this.conditionA = conditionA;
        this.conditionC = conditionC;
    }

    public void run() {
        lock.lock();
        try {
            for (int i = 0; i < 10; i++) {
                System.out.println(3);
                conditionA.signal();
                conditionC.await();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }
}
