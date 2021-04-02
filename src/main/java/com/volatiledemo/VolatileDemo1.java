package com.volatiledemo;


/**
 * 可见性是指：一个线程对共享变量值的修改，能够及时的被其他线程看到
 * 那什么又是共享值：如果一个变量在多个线程的工作内存中都存在副本，那么这个变量就是这几个线程的共享变量
 *
 * 在谈可见性问题前，先介绍一下Java内存模型(JMM)
 * 每个线程创建时，jvm都会为其创建一个工作内存，工作内存主要包含两个部分：一个是属于该线程私有的栈，另外一个是对主存部分变量拷贝的寄存器(包括程序计数器PC和cup工作的高速缓存区)
 * 线程对变量的所有操作都必须在工作内存中进行，而不能直接读写主内存中的变量。
 * 线程之间无法直接访问对方的工作内存中的变量，线程间变量的传递均需要通过主内存来完成.
 *
 * 可见性问题：
 * 在多线程应用程序中，为了提高性能，每个线程在处理变量时可能会将变量从主存复制到CPU缓存中。如果您的计算机包含多个CPU，每个线程可以在不同的CPU上运行。这意味着，每个线程可以将变量复制到不同CPU的CPU缓存中。
 * 这种机制就造成了一个问题：假设现在两个或多个线程访问同一个共享对象，该对象包含一个计数器，现在只有线程1增加了计数器变量，但是线程1和线程2都可以不同时的获取计数器变量，如果计数器变量没有被声明为volatile，
 * 那么就不能保证何时计数器变量的值被从CPU缓存写回主存。这意味着，CPU缓存中的计数器变量值可能与主存中的不一样。
 * 线程没有看到变量的最新值，因为它还没有被另一个线程写回主存，这个问题被称为“可见性”问题。一个线程的更新对其他线程是不可见的。
 */
public class VolatileDemo1 {

    /**
     * 案例：有三个线程A、B、C，顺序为从A到C，线程A最早读到共享变量，并拷贝到自己的工作内存中，后面线程B虽然修改了共享变量的值，线程A读取到的
     *      也还是一开始拷贝进来的副本，并没有重新去主内存中读取最新值，而线程C最后执行，读取到了线程B修改后的共享变量的值。该案例很好的解释了多线程间的可见性问题。
     *
     *      如果用关键字volatile修饰共享变量，则线程A也能刷新拷贝到内存中的最新值。
     *      通过声明flag为volatile，所有对flag变量的写操作都将立即写会主内存中去。同样，对flag变量的所有读取都将直接从主内存中读取。
     */

    //共享变量，被线程所共享，大家都能读取
    private static boolean flag = false;

    //关键字volatile，保证线程间的可见性
    //private static volatile boolean flag = false;

    public static void main(String[] args) {

        //线程A最先执行，死循环直到读到共享变量为ture的时候，停止运行
        new Thread(() -> {
            while (true) {
                if (flag) {
                    System.out.println(Thread.currentThread().getName() + " 读取到修改后的值，并终止循环");
                    break;
                }
            }
        }, "A").start();

        //线程B第二个执行，修改共享变量为true
        new Thread(() -> {
            try {
                //保证上面的线程已经开始运行
                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
            flag = true;
            System.out.println(Thread.currentThread().getName() + " 修改标记成功");
        }, "B").start();

        //线程C最后执行，读取共享变量值
        new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " 读取到的标记为" + flag);
        }, "C").start();
    }
}
