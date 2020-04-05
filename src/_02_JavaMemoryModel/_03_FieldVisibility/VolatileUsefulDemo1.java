package _02_JavaMemoryModel._03_FieldVisibility;

/*
 * 我们都知道, 对单个的volatile变量的读/写具有原子性, 对volatile变量的单个读/写,
 * 等价于使用同一个监视器锁对这些单个读/写操作做了同步;
 * 具体来说, volatile关键字适用于以下场合:
 * 1. boolean flag
 *    如果一个共享变量自始至终只会被各个线程进行单次的读或写, 例如一个boolean类型的标记位变量,
 *    那么就可以用volatile来代替synchronized或代替原子变量, 因为单个的对volatile变量的读/写具有原子性,
 *    volatile又可以保证可见性, 所以就足以保证线程安全(而且volatile是一种轻量级的同步, 它不会产生线程的上下文切换,
 *    比synchronized效率更高);
 * 2. volatile变量作为刷新之前变量的触发器
 *
 * 这里演示volatile适用场合一;
 * 对volatile的适用场合一做一下补充描述, 适用场合一并不是说volatile适用于boolean变量,
 * 而是说volatile适用于仅仅对变量做无需考虑当前变量状态的赋值(即直接赋值, 例如setDone()的这种赋值);
 * 而如果下面的run()方法里需要执行类似于flipDone()这种的计算, volatile是没用的(volatile无法保证其原子性);
 */

import java.util.concurrent.atomic.AtomicInteger;

public class VolatileUsefulDemo1 implements Runnable {
    volatile boolean done = false;
    AtomicInteger realA = new AtomicInteger();

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            // setDone();
            flipDone();
            realA.incrementAndGet();
        }
    }

    private void setDone() {
        done = true;  // 具有原子性
    }

    private void flipDone() {
        done = !done;  // 不具有原子性
    }

    public static void main(String[] args) throws InterruptedException {
        VolatileUsefulDemo1 instance = new VolatileUsefulDemo1();
        Thread t1 = new Thread(instance);
        Thread t2 = new Thread(instance);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("done = " + instance.done);  // 如果run()里执行的是flipDone(), 结果就不确定
        System.out.println("realA = " + instance.realA);
    }
}
