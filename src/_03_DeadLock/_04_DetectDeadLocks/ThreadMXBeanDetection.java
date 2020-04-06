package _03_DeadLock._04_DetectDeadLocks;

/*
 * 使用ThreadMXBean检测死锁;
 */

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.TimeUnit;

public class ThreadMXBeanDetection implements Runnable {
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

    public static void main(String[] args) throws InterruptedException {
        ThreadMXBeanDetection instance = new ThreadMXBeanDetection();
        new Thread(instance, "Thread_A").start();
        new Thread(instance, "Thread_B").start();
        TimeUnit.SECONDS.sleep(1);

        // 检测死锁
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        long[] deadlockedThreads = threadMXBean.findDeadlockedThreads();
        if (deadlockedThreads != null && deadlockedThreads.length > 0) {
            System.out.print("陷入死锁的线程: ");
            for (int i = 0; i < deadlockedThreads.length; i++) {
                ThreadInfo threadInfo = threadMXBean.getThreadInfo(deadlockedThreads[i]);
                System.out.print(threadInfo.getThreadName());
                if (i != deadlockedThreads.length - 1) {
                    System.out.print(", ");
                }
            }
        }
    }
}
