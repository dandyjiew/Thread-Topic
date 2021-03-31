package com.createthread;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 使用Spring异步方法的方式，可以说是相当地方便，适用于前后逻辑不相关联的适合用异步调用的一些方法，比如发送短信的功能。
 */
@SpringBootApplication
@EnableAsync
public class Method9 {

    //需要给启动类加上 @EnableAsync
    //其次，在方法上添加 @Async
    //详细可看：https://blog.csdn.net/d980206432/article/details/114366343
}


