package com.createthread;


/**
 * 创建线程方式一：继承Thread类，并重写run()方法，run方法就代表了线程需要执行的任务，不重写的话，继承父类的run方法，空输出
 * 优势：编写简单
 * 劣势：因为线程已经继承了Thread类，所以不能再继承其他父类(https://blog.csdn.net/sinat_27933301/article/details/69944286原文链接)
 */
public class Method1 {
    public static void main(String[] args) {
        MyThread1 myThread1 = new MyThread1();
        myThread1.start();

        //可以发现，除了自己生成的线程运行了，其实还有隐藏的主线程main线程
        System.out.println(Thread.currentThread().getName());

        MyThread2 myThread2 = new MyThread2("我的线程2");
        myThread2.start();

        //有多种方式可以给线程赋名称
        MyThread2 myThread3 = new MyThread2();
        myThread3.setName("我的线程3");
        myThread3.start();
    }
}

class MyThread1 extends Thread {
    @Override
    public void run() {
        this.setName("我的线程1");
        System.out.println(this.getName() + "开始运行了");
    }
}

class MyThread2 extends Thread {

    public MyThread2() {

    }

    public MyThread2(String name) {
        super(name);
    }

    @Override
    public void run() {
        System.out.println(this.getName() + "开始运行了");
    }
}
