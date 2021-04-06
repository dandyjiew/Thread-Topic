package com.synchronizeddemo;

/**
 * 在Demo1中，采用的是对象锁，在demo2中，使用了两个对象，结果不再是顺序发出，线程不安全。
 */
public class SynchronizedDemo2 {

    public static void main(String[] args) {
        Sender2 senderA = new Sender2();
        Sender2 senderB = new Sender2();

        new Thread(() -> {
            senderA.send(Thread.currentThread().getName() + " 登场");
        }, "A").start();

        new Thread(() -> {
            senderB.send(Thread.currentThread().getName() + "登场");
        }, "B").start();
    }
}

class Sender2 {
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












