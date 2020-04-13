package _07_Lock._02_LockDemos;

/*
 * 演示lockInterruptibly()在等待锁的过程中可以被中断;
 */

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockInterruptiblyDemo implements Runnable {
    private static Lock lock = new ReentrantLock();

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "尝试获取锁");
        try {
            lock.lockInterruptibly();
            try {
                System.out.println(Thread.currentThread().getName() + "已获取锁");
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                /*
                 * 注意这个catch和下面的catch是不同的, 两个都要写;
                 * 这个catch是为了响应当线程执行上面的休眠语句(TimeUnit.SECONDS.sleep(5))时被中断,
                 * 下面的catch是为了响应当线程执行开头的尝试获得锁的语句(lock.lockInterruptibly())时被中断;
                 */
                System.err.println(Thread.currentThread().getName() + "睡眠期间被中断");
            } finally {
                lock.unlock();
                System.out.println(Thread.currentThread().getName() + "释放了锁");
            }
        } catch (InterruptedException e) {
            System.err.println(Thread.currentThread().getName() + "尝试获得锁期间被中断");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        LockInterruptiblyDemo instance = new LockInterruptiblyDemo();
        Thread t0 = new Thread(instance);
        Thread t1 = new Thread(instance);
        t0.start();
        t1.start();
        TimeUnit.SECONDS.sleep(2);
        t0.interrupt();
        t1.interrupt();
    }
}
