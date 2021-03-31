package com.createthread;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 使用java.util.concurrent.Executors.newSingleThreadScheduledExecutor()执行定时任务
 */
public class Method6 {

    private static ThreadLocal<SimpleDateFormat> dateFormat = new ThreadLocal<SimpleDateFormat>() {

        @Override
        public SimpleDateFormat initialValue() {

            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        }
    };

    public static void main(String[] args) {

        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println(dateFormat.get().format(new Date()) + " -- 11111111111111");
            }
        }, 2, TimeUnit.SECONDS);

        service.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println(dateFormat.get().format(new Date()) + " -- 2222222222222");
            }
        }, 2, TimeUnit.SECONDS);

        service.scheduleAtFixedRate(new Runnable() {

            @Override
            public void run() {
                System.out.println(dateFormat.get().format(new Date()) + " -- 3333333333333333333333");

            }
        }, 1, 5, TimeUnit.SECONDS);
    }

}
