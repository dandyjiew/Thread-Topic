package com.createthread;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 使用定时器java.util.Timer可以快速地实现定时任务，TimerTask实际上实现了Runnable接口。
 * 场外话：spring中的 @Scheduled注解也可以执行定时任务
 */
public class Method5 {

    public static void main(String[] args) {

        Timer timer = new Timer();
        TimerTask task = new Worker();
        //每隔1秒执行任务
        timer.schedule(task, 0, 1000);

        //也可以直接按匿名函数的方式
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                System.out.println(Thread.currentThread().getName() + " is running");
//            }
//        }, 0l, 1000l);


    }
}


class Worker extends TimerTask {

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " is running");
    }
}