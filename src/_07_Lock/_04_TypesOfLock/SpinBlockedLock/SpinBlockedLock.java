package _07_Lock._04_TypesOfLock.SpinBlockedLock;

/*
 * 自旋锁(SpinLock)与阻塞锁(BlockedLock):
 * 1. 自旋锁
 *    自旋锁没有改变线程的状态(没有放弃CPU的运行时间), 自旋锁是采用让当前线程不停地的在循环体内执行实现的,
 *    当循环的条件被其他线程改变时, 才能进入临界区;
 * 2. 阻塞锁
 *    阻塞锁, 与自旋锁不同, 改变了线程的运行状态; 阻塞锁, 可以说是让线程进入阻塞状态进行等待,
 *    当获得相应的信号(唤醒, 时间)时, 才可以进入线程的准备就绪状态, 准备就绪状态的所有线程, 通过竞争, 进入运行状态;
 *
 * 为什么会有自旋锁:
 * 在阻塞锁的情况中, 阻塞或唤醒一个线程需要操作系统进行一系列操作来完成, 如CPU用户态和核心态的切换、上下文切换等等,
 * 这种状态转换需要消耗处理器时间; 如果同步代码块中的内容实际上是很简单的内容, 状态转换消耗的时间,
 * 有可能比用户代码执行的时间还要长, 那么阻塞锁中的状态转换就显得得不偿失; 即在许多场景中, 同步资源的锁定时间很短,
 * 为了这一小段时间去切换线程状态, 线程挂起和恢复的花费可能会让系统得不偿失; 自旋锁应运而生;
 * 如果物理机器有多个处理器, 能够让两个或多个线程同时并行执行, 我们就可以让后面那个请求锁的线程不放弃CPU的执行时间,
 * 让它去在循环中不停地检测锁是否已经被释放了; 这种做法避免了切换线程(状态)的开销; 这就是自旋锁;
 *
 * 自旋锁的缺点:
 * 上面讲到, 在同步资源锁定时间很短, 或者说线程占用锁的时间很短的场景中, 使用自旋锁可以避免切换线程的开销;
 * 但是在相反的情况下, 如果线程占用锁的时间很长, 那么自旋锁只会白白浪费处理器资源; 在自旋的过程中, 一直会消耗CPU资源,
 * 所以虽然自旋锁的起始开销低于阻塞锁, 但是随着自旋时间的增长, 开销也是线性增长的;
 *
 * 使用自旋锁的例子以及自旋锁的原理:
 * 在java1.5及以上版本的并发框架java.util.concurrent的atomic包下的类基本都是自旋锁的实现;
 * 他们的自旋锁的实现原理是CAS(Compare-And-Swap); AtomicInteger中调用unsafe进行自增操作的源码中的do-while循环就是一个自旋操作,
 * 如果修改过程中遇到其他线程竞争资源导致没修改成功, 就在while里死循环, 直至修改成功; 这其实就是乐观锁的重试策略;
 * 自旋锁不仅可以是乐观锁的策略, 也可以是悲观锁的策略, 比如说在实际场景中使用tryLock()结合while循环;
 *
 * 自旋锁的应用场景:
 * 自旋锁一般用于多核的服务器, 在并发度不是特别高的情况下, 比阻塞锁效率高;
 * 另外, 自旋锁适用于临界区比较短小的情况, 否则如果临界区很大(即线程一旦拿到锁, 很久以后才会释放), 自旋锁是不适合的;
 *
 * MySpinLock.java演示自己实现一个简单的自旋锁;
 */

public class SpinBlockedLock {
    public static void main(String[] args) {
    }
}
