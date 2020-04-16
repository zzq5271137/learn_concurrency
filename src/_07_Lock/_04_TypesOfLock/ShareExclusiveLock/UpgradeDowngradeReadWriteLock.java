package _07_Lock._04_TypesOfLock.ShareExclusiveLock;

/*
 * 读写锁的升降级:
 * 读锁和写锁其实不是平等的, 我们能看出来写锁拥有更大的权力, 是更高级的锁; 读写锁的升降级指的是:
 * 1. 降级:
 *    当一个线程持有写锁, 在不释放写锁的情况下直接去获取读锁;
 * 2. 升级:
 *    当一个线程持有读锁, 在不释放读锁的情况下直接去获取写锁;
 * ReentrantReadWriteLock(Lock的实现类)允许降级, 不允许升级;
 *
 * 此处演示ReentrantReadWriteLock允许降级, 不允许升级, 依然是电影票那个场景;
 */

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class UpgradeDowngradeReadWriteLock {
    private static ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock(false);
    private static ReentrantReadWriteLock.ReadLock readLock = reentrantReadWriteLock.readLock();
    private static ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock.writeLock();

    private static void readWithUpgrade() {
        readLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "得到了读锁, 正在查看电影票");
            TimeUnit.SECONDS.sleep(3);

            // 尝试升级(会失败, 线程会进入阻塞)
            System.out.println(Thread.currentThread().getName() + "尝试获取写锁");
            if (writeLock.tryLock(3, TimeUnit.SECONDS)) {
                System.out.println(Thread.currentThread().getName() + "得到了写锁");
            } else {
                System.out.println(Thread.currentThread().getName() + "获取写锁失败, 暂时放弃");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName() + "电影票查看完毕, 释放读锁");
            readLock.unlock();

            // 只有先释放读锁, 才能去获取写锁
            System.out.println(Thread.currentThread().getName() + "读锁释放完毕, 再次尝试获取写锁");
            writeLock.lock();
            System.out.println(Thread.currentThread().getName() + "得到了写锁");
        }
    }

    private static void writeWithDowngrade() {
        writeLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "得到了写锁, 正在购买电影票");
            TimeUnit.SECONDS.sleep(4);

            // 尝试降级(会成功)
            System.out.println(Thread.currentThread().getName() + "尝试获取读锁");
            readLock.lock();
            System.out.println(Thread.currentThread().getName() + "得到了读锁");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName() + "电影票购买完毕, 释放写锁");
            writeLock.unlock();
        }
    }

    public static void main(String[] args) {
        // 写锁降级成功
        // new Thread(UpgradeDowngradeReadWriteLock::writeWithDowngrade, "WriteThread_1").start();

        // 读锁升级失败, 会陷入阻塞
        new Thread(UpgradeDowngradeReadWriteLock::readWithUpgrade, "ReadThread_1").start();
    }
}
