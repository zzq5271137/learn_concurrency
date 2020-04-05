package _01_EightCores._05_Core5_CoreMethodsInThreadAndObjectClass;

/*
 * Thread类中的sleep()方法:
 * 和wait()方法不同, sleep()方法不会释放锁, 无论是synchronized关键字的monitor锁还是Lock类的锁, 都不会释放;
 *
 * 此处演示sleep()方法不释放Lock类的锁;
 */

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SleepDontReleaseLock implements Runnable {
    private static final Lock LOCK = new ReentrantLock();

    @Override
    public void run() {
        LOCK.lock();
        System.out.println(Thread.currentThread().getName() + "获取到Lock锁");
        try {
            System.out.println(Thread.currentThread().getName() + "开始休眠");
            Thread.sleep(5000);
            System.out.println(Thread.currentThread().getName() + "休眠结束");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            LOCK.unlock(); // 注意一点, 在使用Lock类锁的时候, 一定要在finally里做释放操作
            System.out.println(Thread.currentThread().getName() + "执行结束, 释放Lock锁");
        }
    }

    public static void main(String[] args) {
        Runnable runnable = new SleepDontReleaseLock();
        new Thread(runnable).start();
        new Thread(runnable).start();
    }
}
