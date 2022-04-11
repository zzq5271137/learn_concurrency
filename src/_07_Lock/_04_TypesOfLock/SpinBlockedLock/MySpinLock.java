package _07_Lock._04_TypesOfLock.SpinBlockedLock;

/*
 * 演示自己实现一个简单的自旋锁;
 */

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class MySpinLock {
    private AtomicReference<Thread> sign = new AtomicReference<>();

    public void lock() {
        Thread current = Thread.currentThread();
        while (!sign.compareAndSet(null, current)) {
            System.out.println(Thread.currentThread().getName() + "自旋锁获取失败, 再次尝试");
            try {
                TimeUnit.MILLISECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void unlock() {
        Thread current = Thread.currentThread();
        sign.compareAndSet(current, null);
    }

    public static void main(String[] args) {
        MySpinLock mySpinLock = new MySpinLock();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + "开始尝试获取自旋锁");
                mySpinLock.lock();
                System.out.println(Thread.currentThread().getName() + "成功获取到自旋锁");
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println(Thread.currentThread().getName() + "释放了自旋锁");
                    mySpinLock.unlock();
                }
            }
        };
        new Thread(runnable, "Thread-1").start();
        new Thread(runnable, "Thread-2").start();
    }
}
