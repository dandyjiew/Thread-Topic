# Thread

本项目主要提供多线程的学习，适合刚开始学习多线程的同学们以及近期在准备面试的同学们。

内容有：

- [x] 创建线程的方法
  1. 继承Thread类，并重写run()方法
  2. 实现Runnable接口并覆盖run()方法
  3. 实现Callabe接口
  4. 使用匿名类的方式，一是重写Thread的run()方法，二是传入Runnable的匿名类，三是使用lambda方式
  5. 定时器（java.util.Timer）
  6. 定时器（newSingleThreadScheduledExecutor）
  7. 使用线程池创建线程执行任务
  8. Java8+ 并行计算
  9. Spring异步执行
- [ ] ThreadLocal