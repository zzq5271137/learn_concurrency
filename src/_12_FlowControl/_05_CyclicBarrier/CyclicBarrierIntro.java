package _12_FlowControl._05_CyclicBarrier;

/*
 * CyclicBarrier(循环栅栏):
 * CyclicBarrier和CountDownLatch非常相似, 都可以阻塞一组线程; 当有大量的线程需要相互配合, 分别执行不同的任务,
 * 并且需要最后统一汇总的时候, 我们就可以使用CyclicBarrier; CyclicBarrier可以构造一个集结点, 当某一个线程执行完毕,
 * 它就会到集结点等待, 直到所有线程都到了集结点, 那么该栅栏就被打开, 所有线程再同一出发, 继续执行剩下的任务;
 *
 * CyclicBarrier的主要方法:
 * 1. CyclicBarrier(parties)/CyclicBarrier(parties, runnable)
 *    构造方法, parties指的是等待的个数, 即当有parties个数的线程调用过await()方法时, 就会打开栅栏(唤醒这些线程);
 *    也可以传入第二个参数, 一个Runnable, 当达到打开栅栏的条件时, 会首先执行这个传入的Runnable的run()方法,
 *    执行完这个run()方法后, 再去唤醒等待的所有线程;
 * 2. await()
 *    当线程调用await()方法后, 会进入阻塞状态, 并把CyclicBarrier中的等待计数减一;
 *    如果CyclicBarrier中的等待计数减至零, 会执行创建该CyclicBarrier时传入的Runnable的run()方法(如果传入的话),
 *    并唤醒所有等待的线程, 然后会自动重置等待计数(将等待计数重新赋值为parties), 以迎接下一波的线程;
 * 3. reset()
 *    重置CyclicBarrier中的等待计数(将等待计数重新赋值为parties);
 *
 * CyclicBarrier和CountDownLatch的区别:
 * 1. 作用不同/应用场景不同:
 *    CyclicBarrier需要固定数量的线程都到达了栅栏位置才能继续执行, 它的倒数是自动的;
 *    而CountDownLatch只需要等待数字到0, 它的倒数是通过主动调用countDown()方法;
 *    也就是说, CountDownLatch用于事件, 但CyclicBarrier是用于线程的;
 * 2. 可重用性不同:
 *    CountDownLatch在倒数到0并触发门闩打开后, 就不能再次使用了, 只能创建新的实例;
 *    而CyclicBarrier可以重复使用;
 * 3. CyclicBarrier多了一个功能:
 *    就是可以在打开栅栏前, 做一些工作(传入的Runnable参数);
 *
 * 示例详见CyclicBarrierDemo.java
 */

public class CyclicBarrierIntro {
    public static void main(String[] args) {
    }
}
