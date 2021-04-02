package com.threadlocal;


import java.util.function.Supplier;

/**
 * 有三种方法可以来指定ThreadLocal的初始值
 * 这个初始值将在第一次调用get()时使用，在set()使用新值调用之前
 */

public class ThreadLocalDemo2 {

    /**
     * 第一种方法是为ThreadLocal变量指定初始值的第一种方法是创建ThreadLocal的子类，该子类覆盖其initialValue()方法
     * 创建ThreadLocal子类的最简单方法是在创建ThreadLocal变量的地方创建一个匿名子类
     */
    private static final ThreadLocal myThreadLocal1 = new ThreadLocal<String>() {
        @Override protected String initialValue() {
            return "我是匿名子类创建的";
        }
    };

    /**
     * 未做初始化的ThreadLocal
     */
    private static final ThreadLocal myThreadLocal2 = new ThreadLocal();

    /**
     * 第二种方法是使用静态工厂方法，Supplier将供应接口实现作为参数传递
     */
    private static final ThreadLocal myThreadLocal3 = ThreadLocal.withInitial(
            new Supplier<String>() {
                @Override
                public String get() {
                    return "我是Supplier作为参数传递实现的";
                }
            }
    );

    /**
     * 第三种方法是惰性设置初始值。
     * 有时候可能不能使用设置初始值的标准方法，例如需要一些在创建ThreadLocal变量时不可用的
     */

    /**
     * 由于Supplier是一个功能性接口，所以可以使用Java Lambda表达式实现它
     */
    private static final ThreadLocal myThreadLocal4 = ThreadLocal.withInitial(() -> "我是Lambda表达式实现的");

    public static void main(String[] args) {
        System.out.println(myThreadLocal1.get());
        System.out.println(myThreadLocal2.get());
        myThreadLocal1.set("主线程修改了初始值");
        new Thread(() -> {
            //可以发现，就算主线程修改了值，新建线程获取到的还是初始值
            System.out.println("子类获取值：" + myThreadLocal1.get());
        }).start();
        System.out.println(myThreadLocal1.get());
        System.out.println(myThreadLocal3.get());
        System.out.println(myThreadLocal4.get());
    }

}
