package _07_Lock._04_TypesOfLock.FairUnfairLock;

/*
 * 锁的等待队列:
 * 首先, 每一把锁都有一个等待队列, 当锁已经被持有时, 此时其他线程请求该锁, 就会进入等待队列的队尾(并进入阻塞状态);
 * 当锁被当前持有者释放时, 会唤醒等待队列队首的那个线程(即唤醒等待时间最长的那个线程);
 * 公平与非公平指的就是, 在释放锁的那一刻, 允不允许一个不在等待队列中的线程(即在释放锁的那一刻正好去请求锁的线程),
 * 去拥有抢锁的权力;
 *
 * 什么是公平锁与非公平锁:
 * 公平指的是, 严格按照线程请求的顺序(也即等待队列的顺序)来分配锁, 所有请求该锁的线程一律进入等待队列中排队, 无例外;
 * 非公平指的是, 不完全按照线程请求的顺序, 在一定的情况下, 可以插队; 注意, 非公平并不是指完全随机分配锁(完全不公平),
 * 而是在一定的情况下可以插队; 非公平也同样不提倡"插队", 所以只有在"合适的时机"才能插队, 而不是盲目插队;
 *
 * 为什么要有非公平锁:
 * 设计出非公平锁, 是为了提高效率, 避免线程唤醒时带来的空档期; 举个例子而言, 这里有A、B、C三个线程,
 * A持有一个Lock锁, 这时B来请求Lock锁, 因为该锁已经被线程A持有, 所以B会进入WAITING状态,
 * (注意B不是进入BLOCKED状态, 之前讲过, 只有去请求synchronized的Monitor锁而该锁被其他线程持有时,
 * 才会进入BLOCKED状态, BLOCKED状态只针对synchronized关键字); 当A将锁释放时, B线程将会被唤醒,
 * 如果正在此时, C线程也请求这把锁, 由于唤醒B线程需要一个过程, 而C线程正处在运行阶段, 即C是可以立刻执行的,
 * 所以我们允许这时的C线程免去在等待队列中排队的过程, 而直接获得抢锁的权力, 即允许线程C的插队; 这是一种双赢的局面,
 * 因为假设C线程成功获得锁(插队成功), 那么当C执行完成并释放掉锁之后, 从时间上讲, B线程可能才成功被唤醒,
 * 所以这里把资源的浪费降到了最小; 这就是非公平锁带来的好处;
 * 注意一点, 上面说的不是特别清楚, 当某线程将锁释放的那一刻, 正好有线程想要请求该锁(即想要插队),
 * 插队的线程只是获得了免去进入等待队列而去直接抢锁的权力, 并不是说这把锁一定会分配给插队的线程;
 * 有可能的情况是, 插队的线程在抢锁的过程中没有抢过等待队列中的第一个线程(即被唤醒的线程被成功唤醒并抢到了锁), 这也是有可能的,
 * 那么在这种情况, 插队的线程插队失败, 就把它安排进等待队列的末尾; 如果是插队成功的情况,
 * 那么在等待队列中头部的那个被唤醒的线程被唤醒后, 发现锁依然被占用着, 那么它会重新回到等待队列(重新进入WAITING状态),
 * 当然, 它的位置还是会在等待队列的最前面, 下一次唤醒的还是它;
 *
 * Lock接口的实现类ReentrantLock默认是非公平锁; 如果在创建ReentrantLock时, 参数填写为true, 那么就会是公平锁;
 *
 * 对于公平锁和非公平锁, 有一个特列, 就是Lock接口中的tryLock()方法, 它不遵守设定的公平规则(公平与非公平);
 * 例如, 当有线程执行tryLock()的时候, 一旦有线程释放了锁, 那么这个正在tryLock的线程就能立即获得锁,
 * 即使在它之前已经有其他线程等待在队列里了; 简单来说, tryLock()方法是带着插队属性的;
 *
 * 对比公平和非公平的优缺点:
 * 1. 公平锁:
 *    优势: 各个线程公平平等, 每个线程在等待一段时间后, 总有执行的机会;
 *    劣势: 更慢, 吞吐量更小;
 * 2. 非公平锁:
 *    优势: 更快, 吞吐量更大;
 *    劣势: 有可能产生线程饥饿, 也就是某些线程在长时间内始终得不到运行;
 *
 * 下面的例子分别演示了使用公平锁与非公平锁的情况, 注意观察控制台打印的顺序的不同;
 */

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class PrintQueue {
    /*
     * ReentrantLock的构造函数传入true, 就是公平锁, 不传参或者传入false, 就是非公平锁;
     * 在这里例子中, 当使用公平锁时, 由于执行一次打印任务需要打印两份, 而且两份打印的锁是分开获得与释放的,
     * 所以这10个线程的执行流程是: 线程0首先开始打印第一份(因为线程是按顺序启动的), 线程1~9依次排在等待锁的队列中,
     * 当线程0打印完第一份之后, 释放锁, 这时线程1会得到锁, 因为它排在等待队列的最前面, 线程1就开始打印第一份,
     * 而线程0则排到队列末尾, 就这样线程1~9开始按顺序打印第一份, 打印完第一份也都重新去排队;
     * 直到线程9打印完第一份, 这时重新轮到线程0, 因为此时线程0排在队列最前面, 线程0得到锁并打印第二份,
     * 然后线程1~9按顺序打印第二份, 直到运行结束;
     * 而当使用非公平锁时, 打印顺序发生了很大变化: 还是线程0首先开始打印第一份, 线程1~9依次排在队列中,
     * 当线程0打印完第一份并释放掉锁之后, 线程0会立刻再去请求这把锁(打印第二份), 而此时正在等待队列最前面的线程1也将被唤醒,
     * 由于使用了非公平锁, 而线程0此时正在运行且正在请求锁, 所以JVM允许线程0去插队(免去线程0重新排队而让它去直接抢锁);
     * 大概率下线程0是会抢锁成功的, 因为唤醒线程1可能需要一定的时间, 所以大概率下线程0会抢到锁然后直接继续打印第二份,
     * 而被唤醒的线程1发现锁依然被占用着, 所以重新进入WAITING状态(还是在等待队列的头部); 当线程0打印完第二份再释放掉锁后,
     * 线程1才被再次唤醒并得到锁(因为此时没人插队了, 所以线程1被唤醒后一定会成功抢到锁), 去打印第一份;
     * 所以, 在非公平锁的情况下, 大概率下线程0~9会按顺序一次性地打印完自己的两份(也不排除抢锁失败的可能, 当然只是小概率);
     */
    // private static Lock queueLock = new ReentrantLock(true);
    private static Lock queueLock = new ReentrantLock(false);

    public void printJob(Object document) {
        queueLock.lock();
        try {
            int duration = new Random().nextInt(10) + 1;
            System.out.println(Thread.currentThread().getName() + "正在打印第一份, 需要" + duration + "秒");
            TimeUnit.SECONDS.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            queueLock.unlock();
        }

        // 模拟一次打印需要打印两份
        queueLock.lock();
        try {
            int duration = new Random().nextInt(10) + 1;
            System.out.println(Thread.currentThread().getName() + "正在打印第二份, 需要" + duration + "秒");
            TimeUnit.SECONDS.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            queueLock.unlock();
        }
    }
}

class Printer implements Runnable {
    private PrintQueue printQueue;

    public Printer(PrintQueue printQueue) {
        this.printQueue = printQueue;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "开始打印");
        printQueue.printJob(new Object());
        System.out.println(Thread.currentThread().getName() + "完成打印");
    }
}

public class FairUnfairLock {
    public static void main(String[] args) throws InterruptedException {
        PrintQueue printQueue = new PrintQueue();
        Thread[] threads = new Thread[10];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new Printer(printQueue));
        }
        for (Thread thread : threads) {
            thread.start();
            Thread.sleep(100);  // 为了让线程按顺序启动, 这里稍稍休眠一小会儿
        }
    }
}
