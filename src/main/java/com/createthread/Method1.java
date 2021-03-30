package com.createthread;


/**
 * 创建线程方式一：继承Thread类，并重写run()方法，run方法就代表了线程需要执行的任务，不重写的话，继承父类的run方法，空输出
 * 优势：编写简单，如果需要访问当前线程，则无需使用 Thread.currentThread()方法，直接使用this即可获取当前线程
 * 劣势：因为线程已经继承了Thread类，所以不能再继承其他父类
 */
public class Method1 {
    public static void main(String[] args) throws InterruptedException {
        MyThread1 myThread1 = new MyThread1();
        myThread1.start();

        //可以发现，除了自己生成的线程运行了，其实还有隐藏的主线程main线程
        //Java中的主线程是当程序启动时开始执行的线程。所有的子线程都是从主线程衍生出来的，主线程是最后一个完成执行的线程。
        System.out.println(Thread.currentThread().getName());

        MyThread2 myThread2 = new MyThread2("我的线程2");
        myThread2.start();

        //有多种方式可以给线程赋名称
        MyThread2 myThread3 = new MyThread2();
        myThread3.setName("我的线程3");
        myThread3.start();

        //先让主线程休眠，确保其他线程已经结束，可以看出，主线程是最后一个完成执行的线程。
        Thread.sleep(50);
        System.out.println("主线程结束");
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
