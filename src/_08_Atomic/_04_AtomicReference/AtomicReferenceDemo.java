package _08_Atomic._04_AtomicReference;

/*
 * 这是之前SpinLock的例子, 即自己实现一个简单的自旋锁;
 */

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class AtomicReferenceDemo {
    private AtomicReference<Thread> sign = new AtomicReference<>();

    public void lock() {
        Thread current = Thread.currentThread();
        while (!sign.compareAndSet(null, current)) {
        }
    }

    public void unlock() {
        Thread current = Thread.currentThread();
        sign.compareAndSet(current, null);
    }

    public static void main(String[] args) {
        AtomicReferenceDemo mySpinLock = new AtomicReferenceDemo();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + "开始尝试获取自旋锁");
                mySpinLock.lock();
                System.out.println(Thread.currentThread().getName() + "成功获取到自旋锁");
                try {
                    TimeUnit.SECONDS.sleep(3);
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
