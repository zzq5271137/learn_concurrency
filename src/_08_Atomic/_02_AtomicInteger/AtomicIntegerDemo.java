package _08_Atomic._02_AtomicInteger;

/*
 * 演示AtomicInteger的基本用法, 对比非原子类的线程安全问题, 使用了原子类之后, 不需要加锁, 也可以保证线程安全;
 */

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerDemo implements Runnable {
    private static final AtomicInteger atomicCount = new AtomicInteger();
    private static volatile int normalCount = 0;

    public void incrementAtomic() {
        // atomicCount.getAndIncrement();  // 并没有加锁, 但这也是线程安全的

        // 这些操作也都是具有原子性的
        // atomicCount.getAndDecrement();
        atomicCount.getAndAdd(-2);
    }

    public void incrementNormal() {
        normalCount++;  // 如果不加锁, 这个普通类型变量的自增是线程不安全的;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            incrementAtomic();
            incrementNormal();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        AtomicIntegerDemo instance = new AtomicIntegerDemo();
        Thread t1 = new Thread(instance);
        Thread t2 = new Thread(instance);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("atomicCount = " + atomicCount.get());
        System.out.println("normalCount = " + normalCount);
    }
}
