package _01_EightCores._04_Core4_LifeCycle.SynchronizedDemos._02_HowToUse;

/*
 * 类锁示例之静态方法形式:
 * synchronized关键字加在静态方法上, 给这个静态方法所在的类加锁
 *
 * 原理:
 * 一个Java类可能有很多个对象, 但是只有一个对应的Class对象;
 * 本质上, 所谓的类锁, 不过是该类对应的Class对象的锁而已;
 * 类锁在同一时刻只能被一个对象拥有;
 */

public class SynchronizedClassStaticMethod implements Runnable {
    static SynchronizedClassStaticMethod instance1 = new SynchronizedClassStaticMethod();
    static SynchronizedClassStaticMethod instance2 = new SynchronizedClassStaticMethod();

    public static synchronized void func() throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + "进入func");
        Thread.sleep(3000);
        System.out.println(Thread.currentThread().getName() + "离开func");
    }

    @Override
    public void run() {
        try {
            func();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(instance1);
        Thread t2 = new Thread(instance2);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("done");
    }
}
