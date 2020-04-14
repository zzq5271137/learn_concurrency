package _07_Lock._04_TypesOfLock.ShareExclusiveLock;

/*
 * 什么是共享锁和排他锁:
 * 1. 排他锁, 又称独占锁、独享锁, 当某一线程获取了排他锁之后, 既可以读又可以写, 但是此时其他线程无法获得这把锁,
 *    其他线程既无法读又无法写; 也就是, 排他锁只能由一个线程持有;
 * 2. 共享锁, 又称读锁, 获得共享锁之后, 可以查看但无法修改和删除数据, 而且其他线程此时也可以获取到共享锁,
 *    也可以查看但无法修改和删除数据; 也就是, 共享锁能够由多个线程同时持有;
 * 共享锁和排他锁的典型例子是Lock接口的一个实现类, ReentrantReadWriteLock读写锁, 其中读锁是共享锁, 写锁是排他锁;
 *
 * ReentrantReadWriteLock读写锁的作用:
 * 在没有读写锁之前, 我们假设使用ReentrantLock, 那么虽然我们保证了线程安全, 但是也浪费了一定的资源,
 * 即, 当只有多个读操作同时进行的情况, 并没有线程安全问题的隐患, 但ReentrantLock依然对资源进行了排他处理;
 * 如果我们使用读写锁, 在读的地方使用读锁, 在写的地方使用写锁, 灵活控制, 没有写锁的情况下, 读是无阻塞的,
 * 这样能够提高程序的执行效率;
 *
 * 读写锁具体规则:
 * 总的来说:
 * 1. 读锁和写锁是互斥的, 即读锁和写锁不能同时存在;
 * 2. 读锁可以由多个线程同时持有, 写锁只能由一个线程持有;
 * 3. 获取读锁后, 只能读, 获取写锁后, 既能读又能写;
 * 具体来说:
 * 1. 如果多个线程只申请读锁, 那么他们都可以申请到;
 * 2. 如果有一个线程已经占用了读锁, 则此时其他线程若申请写锁, 申请写锁的线程会一直等待读锁的释放;
 * 3. 如果有一个线程已经占用了写锁, 则此时其他线程若申请读锁或写锁, 申请锁的线程会一直等待写锁的释放;
 * 总结一句话, 要么是一个或多个线程同时有读锁, 要么是一个线程有写锁, 就这两种情况(要么多读, 要么一写);
 * 换一种思路理解读写锁也可以, 那就是, 读写锁只是一把锁, 但是有两种锁定方式, 读锁定和写锁定;
 * 读写锁可以同时被一个或多个线程以读锁定的方式持有, 也可以被单一线程以写锁定的方式持有, 就这两种情况;
 * 实际上读写锁是两把锁(但是保护的是同一资源), 上面那种思路只是为了方便理解;
 *
 * 此处演示使用ReentrantReadWriteLock模拟购买电影票;
 */

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ShareExclusiveLock {
    // 读写锁(读锁和写锁)的创建方法
    private static ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
    private static ReentrantReadWriteLock.ReadLock readLock = reentrantReadWriteLock.readLock();
    private static ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock.writeLock();

    private static void read() {
        readLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "得到了读锁, 正在查看电影票");
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName() + "电影票查看完毕, 释放读锁");
            readLock.unlock();
        }
    }

    private static void write() {
        writeLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "得到了写锁, 正在购买电影票");
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName() + "电影票购买完毕, 释放写锁");
            writeLock.unlock();
        }
    }

    public static void main(String[] args) {
        new Thread(ShareExclusiveLock::read, "ReadThread_1").start();
        new Thread(ShareExclusiveLock::read, "ReadThread_2").start();
        new Thread(ShareExclusiveLock::write, "WriteThread_1").start();
        new Thread(ShareExclusiveLock::write, "WriteThread_2").start();
    }
}
