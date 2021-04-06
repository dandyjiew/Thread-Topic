package com.synchronizeddemo;


/**
 * 在Demo3中，使用synchronized修饰了静态方法，当多个线程同时访问这个方法时，需要同步等待。
 * 但是，A线程访问synchronized修饰的静态方法的同时，B线程可以访问synchronized修饰的非静态方法
 * 注意：静态方法是属于类的，普通方法是属于对象本身的，所以一个是对象锁，一个是class锁，不会影响。
 */
public class SynchronizedDemo4 {

    public static void main(String[] args) {
        Sender4 senderA = new Sender4();
        Sender4 senderB = new Sender4();
        Sender4 senderC = new Sender4();
        Sender4 senderD = new Sender4();


        new Thread(() -> {
            senderA.sendUseStatic(Thread.currentThread().getName() + " 登场");
        }, "A").start();

        new Thread(() -> {
            senderB.sendNoStatic(Thread.currentThread().getName() + "登场");
        }, "B").start();

        new Thread(() -> {
            senderC.sendNormalMethod(Thread.currentThread().getName() + "登场");
        }, "C").start();

        //这里线程D和线程A是同步的，需要等线程A完成后，才能到线程D
        new Thread(() -> {
            senderD.sendUseStatic(Thread.currentThread().getName() + " 登场");
        }, "D").start();
    }
}

class Sender4 {
    public synchronized static void sendUseStatic(String msg) {
        System.out.println("准备发送: " + msg);
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("发送出: " + msg);
    }

    public synchronized void sendNoStatic(String msg) {
        System.out.println("准备发送: " + msg);
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("发送出: " + msg);
    }

    public void sendNormalMethod(String msg) {
        System.out.println("准备发送: " + msg);
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("发送出: " + msg);
    }
}
