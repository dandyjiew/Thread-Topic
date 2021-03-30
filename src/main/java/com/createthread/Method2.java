package com.createthread;


/**
 * 创建线程方式二：可以通过实现Runnable接口并覆盖run()方法来创建线程。然后可以创建一个线程对象并调用start()方法。
 * 优势：还可以继承其他类，或者实现其他接口；多个线程可以共享同一个target对象，所以非常适合多个相同线程来处理同一份资源的情况。
 * 劣势：编程稍稍复杂，如果需要访问当前线程，则必须使用Thread.currentThread()方法
 */
public class Method2 {

    public static void main(String[] args) {

        MyThread myThread = new MyThread();

        // 这里与继承Thread方式不同，它是先实例化一个实现Runnable接口的线程对象myThread，然后将它作为Thread实例化的对象
        // 的参数创建thread，通过thread执行run方法逻辑
        // myThread没有start方法，因为Runnable接口中只有一个抽象方法run，而且必须实现run方法，否则编译会报错，这点也是与方式一最大的不同。
        Thread thread = new Thread(myThread);
        thread.start();
    }
}

class MyThread implements Runnable {

    @Override
    public void run() {
        System.out.println("线程开始执行任务");
    }
}
