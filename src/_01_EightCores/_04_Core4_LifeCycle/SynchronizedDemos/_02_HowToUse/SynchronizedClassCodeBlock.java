package _01_EightCores._04_Core4_LifeCycle.SynchronizedDemos._02_HowToUse;

/*
 * 类锁示例之代码块形式:
 * synchronized关键字加在代码块上, 需要指定对哪个类加锁(在synchronized()的括号中指定, 如Person.class)
 *
 * 原理:
 * 一个Java类可能有很多个对象, 但是只有一个对应的Class对象;
 * 本质上, 所谓的类锁, 不过是该类对应的Class对象的锁而已;
 * 类锁在同一时刻只能被一个对象拥有;
 */

public class SynchronizedClassCodeBlock implements Runnable {
    static SynchronizedClassCodeBlock instance1 = new SynchronizedClassCodeBlock();
    static SynchronizedClassCodeBlock instance2 = new SynchronizedClassCodeBlock();

    private void func() throws InterruptedException {
        synchronized (SynchronizedClassCodeBlock.class) {
            System.out.println(Thread.currentThread().getName() + "进入func");
            Thread.sleep(3000);
            System.out.println(Thread.currentThread().getName() + "离开func");
        }
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
