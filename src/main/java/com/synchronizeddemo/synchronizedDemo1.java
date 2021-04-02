package com.synchronizeddemo;

import org.springframework.http.converter.json.GsonBuilderUtils;

/**
 * 多线程程序经常会出现这样的情况:多个线程试图访问相同的资源，最终产生错误的和不可预见的结果。
 * 因此，需要通过某种同步方法来确保在给定的时间点只有一个线程可以访问资源。
 *
 * Java提供了一种创建线程并通过使用同步块来同步它们的任务的方法。Java中的同步块是用Synchronized关键字标记的。
 * Java中的同步块在某些对象上同步。在同一个对象上同步的所有同步块一次只能有一个线程在其中执行。所有其他试图进入同步块的线程都会被阻塞，直到同步块中的线程退出该块。
 *
 * 这种同步是在Java中通过一个称为监视器的概念实现的。在给定的时间内，只有一个线程可以拥有监视器。当一个线程获得一个锁时，它就被称为已经进入了监视器。所有其他试图进入锁定的监视器的线程都将挂起，直到第一个线程退出监视器。
 *
 * 根据Synchronized用的位置可以有这些使用场景：
 * 方法:
 *  1. 实例方法（锁住类的实例对象）
 *  2. 静态方法（锁住类的对象）
 *
 * 代码块:
 *  1. 实例对象（锁住类的实例对象）
 *  2. class对象（类对象）
 *  3. 任意实例对象Object（实例对象Object）
 *
 *  案例1：顺序发送信息，线程A完成发送后，再由线程B发送。 （锁住实例方法）
 */
public class synchronizedDemo1 {

    public static void main(String[] args) {
        Sender sender = new Sender();

        new Thread(() -> {
            sender.send(Thread.currentThread().getName() + " 登场");
        }, "A").start();

        new Thread(() -> {
            sender.send(Thread.currentThread().getName() + "登场");
        }, "B").start();
    }

}

class Sender {
    //可以删除synchronized后查看效果
    public synchronized void send(String msg) {
        System.out.println("准备发送: " + msg);
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("发送出: " + msg);
    }
}




