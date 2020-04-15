package _07_Lock._04_TypesOfLock.ShareExclusiveLock;

/*
 * 就读写锁来说, 它的非公平的情况与其它锁不同, 比较特殊;
 *
 * 读写锁的读锁与写锁之间的交互方式:
 * 读写锁之间的交互方式要考虑几个问题:
 * 1. 选择规则/读锁插队策略
 *    比如, 当线程A持有读锁正在读时, 线程B想要写, 它就得在等待队列中等待(因为读锁与写锁的互斥), 然后这时线程C想要获取读锁,
 *    那么是否允许线程C免去在等待队列中排队而去直接获得读锁(因为读锁可以多个线程同时持有), 还是需要排在线程B之后呢?
 *    如果选择允许让线程C插队, 看上去整体的执行效率会更高, 但这会带来什么问题呢?
 * 2. 升降级
 *    读锁和写锁其实不是平等的, 我们能看出来写锁拥有更大的权力; 那我们允许锁的升降级吗?
 *    比如, 当一个线程持有写锁, 允不允许它在不释放写锁的情况下直接去获取读锁呢(即降级)?
 *    又或者, 当一个线程持有读锁, 允不允许它在不释放读锁的情况下直接去获取写锁呢(即升级)?
 *
 * ReentrantReadWriteLock对于上述问题的具体实现是:
 * 1. 不允许读锁插队
 *    准确的说法应该是只有在特定情况下, 才允许读锁插队, 即当等待队列头结点的线程不是请求写锁时, 读锁可以插队;
 * 2. 允许降级, 不允许升级;
 *
 * 读锁插队策略:
 * 首先, 对于公平的读写锁而言, 没有插队这一说法, 因为在公平锁的情况下, 始终是按照等待队列的顺序去分配锁的;
 * 例如就上面的那个例子, 如果等待队列中有人在排队了, 那么线程C就必须跟着排队, 又因为线程B不能立即获得锁(因为读锁与写锁的互斥),
 * 所以即使线程C请求的是读锁, 即使读锁可以同时被多个线程持有, 线程C也必须等待线程B先获得写锁并释放;
 * 这里重点讨论的是非公平的读写锁的情况; 假设这种情况, 线程1正在读取, 线程2想要写入, 拿不到锁,
 * 于是进入等待队列, 线程3不在等待队列中, 此时它想要读取; 如果是在ReentrantLock(Lock的实现类)的非公平的情况中,
 * 线程3是被允许可以在线程1释放锁的一瞬间直接去抢锁的(允许线程3免去在等待队列中排队), 因为唤醒线程2需要时间,
 * 而线程3此时正处在运行状态, 所以允许线程3去插队, 如果插队成功, 那么线程3优先拿到锁然后去执行, 这样有助于提高效率,
 * 避免线程唤醒时带来的空档期; 这是ReentrantLock的非公平的情况(非读写锁的非公平的情况);
 * 对于读写锁而言, 比较特殊, 因为它分为读锁和写锁, 读锁可以由多个线程同时持有, 写锁只能由一个线程持有,
 * 而且读锁和写锁是互斥的; 所以, 就上面那种情况, 如果我们始终允许想要获取读锁的现线程去插队, 他们大概率会立即获得读锁
 * (因为允许多个线程同时持有读锁), 但在等待队列中的请求写锁的线程就会始终得不到运行(因为读锁与写锁是互斥的), 也就是造成了饥饿;
 * 所以ReentrantReadWriteLock采用的策略是不允许读锁的插队(读锁仅仅在特定情况下可以插队); 这个策略的完整描述是:
 * 1. 首先, 写锁可以插队(因为在实际业务场景中, 写的情况会远远少于读的情况)
 *    举个例子, 线程1正持有读锁执行读操作, 此时线程2想要请求写锁, 则线程2进入等待队列;
 *    在线程1释放读锁的那一刻, 线程3想要请求写锁(注意是写锁), 则线程3是有权力插队的, 线程3不用进入等待队列排在线程2后面,
 *    (也就是线程3弥补了唤醒线程2时带来的空档期);
 * 2.读锁仅能在等待队列头结点不是想获得写锁的线程的时候可以插队;
 * (注意看ReentrantReadWriteLock源码中的FairSync和NonfairSync内部类)
 *
 * 这里还是使用ShareExclusiveLock.java中的购买电影票的情景(查看电影票需要读锁, 购买电影票需要写锁),
 * 只不过在主函数中对查看和购买的顺序做了调整, 以演示非公平的读写锁中读锁不能插队的情景;
 * 同ReentrantLock的构造函数一样, 如果不传参或者传入false, 创建的就是非公平锁, 如果传入true, 就是公平锁;
 *
 * ReadLockNonFairBarge.java演示了非公平的读写锁中读锁插队的情景, 详见ReadLockNonFairBarge.java;
 */

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockPolicies {
    // 读写锁(读锁和写锁)的创建方法
    private static ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock(false);
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
        new Thread(ReadWriteLockPolicies::write, "WriteThread_1").start();
        new Thread(ReadWriteLockPolicies::read, "ReadThread_1").start();
        new Thread(ReadWriteLockPolicies::read, "ReadThread_2").start();
        new Thread(ReadWriteLockPolicies::write, "WriteThread_2").start();

        // 这个线程不能插队, 因为此时队列头结点是一个想要获取写锁的线程
        new Thread(ReadWriteLockPolicies::read, "ReadThread_3").start();
    }
}
