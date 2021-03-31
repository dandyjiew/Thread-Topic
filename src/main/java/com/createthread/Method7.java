package com.createthread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 使用线程池创建线程执行任务
 */
public class Method7 {

    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(5);

        for (int i = 0; i < 100; i++) {
            threadPool.execute(new Task());
        }

        threadPool.shutdown();
    }
}

class Task implements Runnable {
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " Thread Run");
    }
}
