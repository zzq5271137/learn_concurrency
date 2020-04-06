package _03_DeadLock._02_Demos;

/*
 * 必定发生死锁的情况演示;
 */

import java.util.concurrent.TimeUnit;

public class SimpleDeadLock implements Runnable {
    static final Object LOCK_1 = new Object();
    static final Object LOCK_2 = new Object();

    @Override
    public void run() {
        String threadName = Thread.currentThread().getName();
        if (threadName.equals("Thread_A")) {
            System.out.println(threadName + "尝试获得LOCK_1");
            synchronized (LOCK_1) {
                System.out.println(threadName + "获得LOCK_1");
                try {
                    TimeUnit.MILLISECONDS.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(threadName + "尝试获得LOCK_2");
                synchronized (LOCK_2) {
                    System.out.println(threadName + "获得LOCK_2");
                }
            }
        }
        if (threadName.equals("Thread_B")) {
            System.out.println(threadName + "尝试获得LOCK_2");
            synchronized (LOCK_2) {
                System.out.println(threadName + "获得LOCK_2");
                try {
                    TimeUnit.MILLISECONDS.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(threadName + "尝试获得LOCK_1");
                synchronized (LOCK_1) {
                    System.out.println(threadName + "获得LOCK_1");
                }
            }
        }
    }

    public static void main(String[] args) {
        SimpleDeadLock instance = new SimpleDeadLock();
        new Thread(instance, "Thread_A").start();
        new Thread(instance, "Thread_B").start();
    }
}
