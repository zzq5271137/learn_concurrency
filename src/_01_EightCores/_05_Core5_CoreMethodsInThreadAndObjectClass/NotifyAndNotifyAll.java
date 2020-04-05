package _01_EightCores._05_Core5_CoreMethodsInThreadAndObjectClass;

/*
 * 展示notify()和notifyAll()的区别:
 * 1. nofity()是随机唤醒一个等待的线程(唤醒哪一个, 取决于JVM的实现逻辑)
 * 2. notifyAll()是唤醒所有等待的线程
 *
 * 也会展示, 先执行start(), 不代表线程先启动;
 *
 * 这里会有3个线程, 线程1和线程2首先会执行wait()阻塞, 线程3会去唤醒他们;
 */

public class NotifyAndNotifyAll implements Runnable {
    public static final Object RESOURCE_A = new Object();

    @Override
    public void run() {
        synchronized (RESOURCE_A) {
            System.out.println(Thread.currentThread().getName() + "进入同步块, 获得RESOURCE_A锁");
            try {
                Thread.sleep(2000);
                System.out.println(Thread.currentThread().getName() + "执行wait(), 释放RESOURCE_A锁");
                RESOURCE_A.wait();
                System.out.println(Thread.currentThread().getName() + "被唤醒, 重新获得RESOURCE_A锁");
                Thread.sleep(2000);
                System.out.println(Thread.currentThread().getName() + "运行结束, 释放RESOURCE_A锁");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Runnable runnable = new NotifyAndNotifyAll();
        Thread t1 = new Thread(runnable);
        Thread t2 = new Thread(runnable);
        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (RESOURCE_A) {
                    System.out.println(Thread.currentThread().getName() + "进入同步块, 获得RESOURCE_A锁");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    RESOURCE_A.notifyAll();
                    System.out.println(Thread.currentThread().getName() + "执行notifyAll()");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "运行结束, 释放RESOURCE_A锁");
                }
            }
        });

        t1.start();
        t2.start();
        Thread.sleep(2200);
        t3.start();
    }
}
