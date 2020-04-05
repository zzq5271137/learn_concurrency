package _01_EightCores._04_Core4_LifeCycle.SynchronizedDemos._02_HowToUse;

/*
 * 对象锁示例之代码块形式:
 * synchronized关键字加在代码块上, 锁对象需要自己指定(在synchronized()的括号中指定)
 */

public class SynchronizedObjectCodeBlock implements Runnable {
    static SynchronizedObjectCodeBlock instance = new SynchronizedObjectCodeBlock();

    final Object LOCK_1 = new Object();
    final Object LOCK_2 = new Object();

    @Override
    public void run() {
        synchronized (LOCK_1) {
            System.out.println(Thread.currentThread().getName() + ": 获得锁lock1");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + ": 释放锁lock1");
        }

        synchronized (LOCK_2) {
            System.out.println(Thread.currentThread().getName() + ": 获得锁lock2");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + ": 释放锁lock2");
        }
    }

    public static void main(String[] args) {
        Thread t1 = new Thread(instance);
        Thread t2 = new Thread(instance);
        t1.start();
        t2.start();
        while (t1.isAlive() || t2.isAlive()) { // 或者可以使用t1.join()和t2.join()
        }
        System.out.println("done");
    }
}
