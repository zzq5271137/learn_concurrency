package _07_Lock._02_LockDemos;

/*
 * Lock锁不会像synchronized一样在异常的时候自动释放锁, 所以我们必须手动释放;
 * 最佳实践是, 在finally里释放锁, 以保证发生异常时锁一定被释放;
 */

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MustUnlock {
    private static final Lock LOCK = new ReentrantLock();

    public static void main(String[] args) {
        LOCK.lock();
        try {
            // 获取到LOCK保护的资源
            System.out.println(Thread.currentThread().getName() + "开始执行...");
        } finally {
            // 手动释放锁
            LOCK.unlock();
        }
    }
}
