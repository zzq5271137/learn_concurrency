package _01_EightCores._04_Core4_LifeCycle.SynchronizedDemos._07_Drawbacks;

/*
 * synchronized关键字的锁的缺陷:
 * 1. 效率低:
 *    锁的释放情况少(只有获得锁的线程执行完成或者抛出异常时才会主动地去释放);
 *    试图获得锁时不能设置超时;
 *    不能中断一个正在试图获得锁的过程;
 * 2. 不够灵活:
 *    加锁和释放锁的时机单一, 即每个锁仅有单一的条件(某个对象), 可能是不够的;
 *    相比之下, 读写锁有更高的灵活性, 它的加锁解锁时机是很灵活的, 在做读操作时, 是不加上这个锁的, 只有写操作会加;
 * 3. 无法提前知道是否会成功获取锁;
 *
 * 相比之下, Lock类的锁会解决上面的问题;
 */

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockDemo {
    public static void main(String[] args) throws InterruptedException {
        Lock lock = new ReentrantLock();

        // 加锁
        lock.lock();

        // 解锁
        lock.unlock();

        // 尝试获得锁, 会提前知道能否成功获得锁
        lock.tryLock();

        // 带参数的tryLock(), 可以设置超时时间
        lock.tryLock(10, TimeUnit.SECONDS);

        // 设置锁的一些条件
        lock.newCondition();
    }
}
