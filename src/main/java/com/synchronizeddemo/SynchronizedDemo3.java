package com.synchronizeddemo;

/**
 * 在Demo2中，使用对象锁是无法锁住不同对象的。
 * 如果修饰的是静态方法的话，其作用的范围是整个静态方法，作用的对象是这个类的所有对象；
 * 也就是说整个类就一个锁，这也和静态的概念相符（静态方法和属性是属于类的而不是具体一个对象的）
 */
public class SynchronizedDemo3 {

    public static void main(String[] args) {
        Sender3 senderA = new Sender3();
        Sender3 senderB = new Sender3();

        new Thread(() -> {
            senderA.send(Thread.currentThread().getName() + " 登场");
        }, "A").start();

        new Thread(() -> {
            senderB.send(Thread.currentThread().getName() + "登场");
        }, "B").start();
    }
}

class Sender3 {
    public synchronized static void send(String msg) {
        System.out.println("准备发送: " + msg);
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("发送出: " + msg);
    }
}