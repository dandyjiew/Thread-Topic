package com.createthread;

import java.util.concurrent.*;

/**
 * 创建线程方式三：callable
 * 不管是方式一还是方式二，缺点都是在执行完任务之后无法获取执行结果（必须通过共享变量或者线程通信方式达到效果，麻烦）
 * 从Java 1.5开始，就提供了Callable(产生结果)和Future(获取结果)，通过它们可以在任务执行完之后得到任务执行结果
 */
public class Method31 {

    public static void main(String[] args) {
        MyCallable callableTask1 = new MyCallable(10, 20, 2000);
        MyCallable callableTask2 = new MyCallable(20, 40, 4000);

        //这里创建固定含有2个线程的线程池，线程池后面会作介绍，这里可以把它想象成可用两个线程
        ExecutorService executor = Executors.newFixedThreadPool(2);

        //futureTask1和futureTask2会异步开始执行
        //异步的概念：
        Future<Integer> future1 = executor.submit(callableTask1);
        Future<Integer> future2 = executor.submit(callableTask2);

        long time = System.currentTimeMillis();
        try {
            //isDone方法可以判断当前任务是否执行完毕
            if (future1.isDone()) {
                System.out.println("task1执行完毕");
            } else {
                //get方法是一个阻塞操作，要等待能获取到结果后才会去执行后续操作
                System.out.println("task1获取得到结果 " + future1.get() + " 共耗时: " + (System.currentTimeMillis() - time));
            }
            //主线程休眠2s后，加上task1要执行的2s，此时task2已经执行完毕。也就是说，task2一直在异步执行。
            System.out.println("主线程在执行任务，共耗时: 2000");
            Thread.sleep(2000);
            if (future2.isDone()) {
                System.out.println("task2执行完毕，共耗时: 4000");
            } else {
                System.out.println("task2获取得到结果 " + future2.get() + " 共耗时: " + (System.currentTimeMillis() - time));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        //task1需要耗时2000，task2需要耗时4000，主线程任务需要耗时2000，如果是同步的话，则完成三个任务总共要耗时8000
        //现在通过异步的方式，将时间缩减到了4000，异步任务之间互不影响，简单来说就是大家一起跑，消耗的时间主要看那个耗时最高的任务，而同步就是一个跑完接着一个跑。
        System.out.println("所有任务执行完毕，总共耗时: " + (System.currentTimeMillis() - time));
        executor.shutdown();
    }

}

class MyCallable implements Callable<Integer> {

    int a, b, sleepTime;

    public MyCallable(int a, int b, int sleepTime) {
        this.a = a;
        this.b = b;
        this.sleepTime = sleepTime;
    }

    @Override
    public Integer call() throws Exception {

        Thread.sleep(sleepTime);
        return a * b;
    }
}
