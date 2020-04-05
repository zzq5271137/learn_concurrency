package _01_EightCores._04_Core4_LifeCycle.SynchronizedDemos._06_Theory;

/*
 * 获取和释放锁的时机(内置锁):
 * 每一个Java对象都可以被用作一个实现同步的锁, 这个锁被称为内置锁, 或者叫做监视器锁;
 * 这个锁是存储在Java对象头里的, 在Java对象头里有一个字段来表明这个对象是否被锁住;
 * 这个存储在Java对象头里的字段, 是一个指向其关联的Monitor对象的指针,
 * 每一个对象都会有一个Monitor对象和他关联, 一旦这个Monitor被持有了之后, 这个对象就会处于锁定状态;
 * 也就是说, 获得锁和释放锁是基于Monitor对象来实现同步方法/代码块的;
 * 对于Monitor对象, 主要有两个指令, 一个是monitorenter, 一个是monitorexit;
 * monitorenter会插入到同步代码块开始的位置, monitorexit会插入到代码块结束或者退出的时候;
 * JVM会保证每一个enter之后必须会有exit和他对应(也许会有多个exit和同一个enter对应, 比如抛出异常的情况);
 * 当线程执行到monitorenter这个指令时, 将会尝试获取这个对象所对应的Monitor对象的所有权(也就是尝试获取这个对象的锁);
 * Monitor对象中记录一个锁关联者和一个计数器;
 * monitorenter指令的三种情况:
 * 1. 如果monitor的计数器为0, 意味着这个Montior对象还没有被任何线程获得, 此线程就会成功获得Montior对象的所有权,
 *    也就是成功获得这把锁, 同时会把montior计数器设置为1, 然后monitor的锁关联者指向这个线程;
 * 2. 如果monitor的计数器不为0, 而且执行monitorenter的线程和monitor的锁关联者所指向的线程相同(即此线程重入了),
 *    就会将monitor计数器累加1;
 * 3. 如果monitor的计数器不为0, 而且执行monitorenter的线程和monitor的锁关联者所指向的线程不相同,
 *    那么那个线程就会进入阻塞状态, 直到monitor计数器变为0, 才回去再次尝试获得这个锁;
 * monitorexit指令:
 * 1. 首先, 执行monitorexit指令的线程必须是这把锁的拥有者;
 * 2. 执行monitorexit指令, 就是将monitor计数器减1;
 * 3. 如果减完之后, monitor计数器变为0, 则该线程不再拥有这个Monitor对象(即释放了这个锁),
 *    会将monitor的锁关联者指针置为null;
 * (详细的monitor运行的机制详见https://blog.csdn.net/mulinsen77/article/details/88635558)
 * 线程在进入同步代码块之前, 会自动获得相应的锁, 并且在退出同步代码块的时候,
 * 会自动释放这把锁(无论是通过正常途径退出还是抛出异常, 都会释放);
 * 获得这个内置锁的唯一途径就是进入到这个锁所保护的同步代码块或者方法中;
 *
 * synchronized的锁是隐式锁, 也就是不需要手动的去lock和unlock, 进入synchronized同步块就是获得锁, 出来就是释放锁;
 * 相比于synchronized的锁, Lock类中的锁是显式锁, 需要手动去lock和unlock;
 * 这里借助Lock类的显示锁来展示synchronized锁的获取和释放的时机;
 * func1()和func2()是等价的;
 */

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SynchronizedToLock {
    Lock lock = new ReentrantLock();

    public synchronized void func1() {
        System.out.println("我是synchronized形式的锁");
    }

    public void func2() {
        lock.lock();
        try { // 使用try/finally是为了模拟synchronized在抛出异常时依然会释放锁
            System.out.println("我是Lock形式的锁");
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        SynchronizedToLock instance = new SynchronizedToLock();
        instance.func1();
        instance.func2();
    }
}
