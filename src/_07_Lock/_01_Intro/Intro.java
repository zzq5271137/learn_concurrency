package _07_Lock._01_Intro;

/*
 * 锁简介:
 * 锁是一种工具, 用于控制对共享资源的访问; Java对象自带了监视器锁(Monitor), 也就是synchronized关键字使用的锁;
 * 平时常用的另外一种锁就是Lock接口下的ReentrantLock类; Lock和synchronized(Montior锁), 这两个最常见的锁,
 * 他们都可以达到线程安全的目的, 但是在使用上和功能上又有较大的不同; 需要明确一点的是, Lock并不是用来替代synchronized的,
 * 而是当使用synchronized不合适或不足以满足我们的需求的时候, 来提供高级功能的;
 * Lock接口中最常见的实现类就是ReentrantLock; 通常情况下, Lock只允许一个线程来访问这个共享资源;
 * 不过有的时候, 一些特殊的实现也可以允许并发访问, 例如ReadWriteLock里面的ReadLock;
 *
 * 为什么需要Lock锁, 为什么synchronized不够用:
 * synchronized有很多劣势:
 * 1). 效率低
 *     锁的释放情况少、试图获得锁时不能设置超时、不能中断一个试图获得锁的过程;
 * 2). 不够灵活(与之相反的, 读写锁更灵活)
 *     加锁和释放锁的时机单一, 每个锁仅有单一的条件(某个对象), 这可能是不够满足我们的需求的;
 * 3). 无法提前知道是否会成功获取锁;
 * Lock接口可以解决以上的问题;
 *
 * Lock接口主要方法介绍:
 * 首先, Lock锁与synchronized的Montior锁的一点很重要的区别是, Lock不会像synchronized一样在发生异常时自动释放锁;
 * 对于Lock锁而言, 无论是加锁还是释放锁都需要我们明确地写出来, 这就是为什么我们通常在try/catch/finally的finally里释放锁,
 * 即在finally里写lock.unlock(), 以保证发生异常时锁一定被释放; 详见MustUnlock.java
 * 在Lock中声明了4个方法来获取锁:
 * 1). lock()
 *     最普通的获取锁的方法, 如果锁已经被其他线程获取, 则进行等待;
 *     lock()方法获取锁的过程不能被中断(和synchronized一样), 这会带来很大的隐患, 一旦陷入死锁,
 *     执行lock()的线程就会陷入永久等待;
 * 2). tryLock()
 *     tryLock()方法用来尝试获取锁; 如果当前锁没有被其他线程占用, 则获取它, 并返回true, 代表获取成功;
 *     否则返回false, 代表获取失败; 相比如lock()方法而言, 这样的方法显然功能更强大了, 我们可以根据是否能获取到锁,
 *     来决定后续程序的行为; 这个方法(没有参数的tryLock())会立刻返回, 即便拿不到锁也不会等待;
 * 3). tryLock(long time, TimeUnit unit)
 *     即, 带参数的tryLock()方法, 与不带参数的tryLock()区别在于, 这个方法有一个超时时间, 拿不到锁会等待, 超时就放弃;
 *     详见TryLockDemo.java
 * 4). lockInterruptibly()
 *     此方法相当于tryLock(long time, TimeUnit unit)方法把超时时间设置为无限;
 *     在等待锁的过程中, 线程可以被中断(可以响应interrupt()请求);
 *     详见LockInterruptiblyDemo.java
 */

public class Intro {
    public static void main(String[] args) {
    }
}
