package _07_Lock._04_TypesOfLock.ReentrantLock;

/*
 * ReentrantLock类的其他方法:
 * 1. isHeldByCurrentThread():
 *    查看锁是否被当前线程持有;
 * 2. getQueueLength():
 *    查看当前正在等待这把锁的队列有多长;
 * 这两个方法一般是开发和调试的时候使用, 上线后一般用不到;
 */

import java.util.concurrent.locks.ReentrantLock;

public class OtherMethodsInReentrantLock {
    private static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        lock.lock();
        System.out.println(lock.isHeldByCurrentThread());
        System.out.println(lock.getQueueLength());
    }
}
