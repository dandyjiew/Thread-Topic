package com.createthread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * FutureTask除了实现了Future接口外还实现了Runnable接口（即可以通过Runnable接口实现线程，也可以通过Future取得线程执行完后的结果），
 * 因此FutureTask也可以直接提交给Executor执行。
 */
public class Method32 {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        MyCallable2 myCallable1 = new MyCallable2(10, 20, 2000);
        MyCallable2 myCallable2 = new MyCallable2(20, 40, 4000);

        FutureTask<Integer> futureTask1 = new FutureTask<>(myCallable1);
        FutureTask<Integer> futureTask2 = new FutureTask<>(myCallable2);

        //也可以不使用线程池
        //Thread thread = new Thread(futureTask1);

        executor.submit(futureTask1);
        executor.submit(futureTask2);
        executor.shutdown();

        try {
            long time = System.currentTimeMillis();
            Thread.sleep(2000);
            System.out.println("主线程在执行其他任务");

            System.out.println("task1获取结果: " + futureTask1.get());
            System.out.println("task2获取结果: " + futureTask2.get());

            System.out.println("总共耗时: " + (System.currentTimeMillis() - time));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class MyCallable2 implements Callable<Integer> {

    int a, b, sleepTime;

    public MyCallable2(int a, int b, int sleepTime) {
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