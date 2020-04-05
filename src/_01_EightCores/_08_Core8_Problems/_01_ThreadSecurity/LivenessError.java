package _01_EightCores._08_Core8_Problems._01_ThreadSecurity;

/*
 * 线程安全问题之活跃性问题(此处演示死锁);
 */

import java.util.concurrent.TimeUnit;

public class LivenessError implements Runnable {
    int flag;
    static final Object LOCK_1 = new Object();
    static final Object LOCK_2 = new Object();

    public LivenessError(int flag) {
        this.flag = flag;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + ", flag = " + flag);
        if (flag == 0) {
            synchronized (LOCK_1) {
                System.out.println(Thread.currentThread().getName() + "获得LOCK_1");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "尝试获得LOCK_2");
                synchronized (LOCK_2) {
                    System.out.println(Thread.currentThread().getName() + "获得LOCK_2");
                }
            }
        }
        if (flag == 1) {
            synchronized (LOCK_2) {
                System.out.println(Thread.currentThread().getName() + "获得LOCK_2");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "尝试获得LOCK_1");
                synchronized (LOCK_1) {
                    System.out.println(Thread.currentThread().getName() + "获得LOCK_1");
                }
            }
        }
    }

    public static void main(String[] args) {
        new Thread(new LivenessError(0)).start();
        new Thread(new LivenessError(1)).start();
    }
}
