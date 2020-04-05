package _02_JavaMemoryModel._03_FieldVisibility;

/*
 * 参考: https://www.infoq.cn/article/java-memory-model-5/
 *
 * 锁的释放-获取建立的happens-before关系:
 * 锁是java并发编程中最重要的同步机制; 锁除了让临界区互斥执行外, 还可以让释放锁的线程向获取同一个锁的线程发送消息;
 * 对于MonitorDemo这份代码, 假设线程A执行writer()方法, 随后线程B执行reader()方法; 根据happens-before规则,
 * 这个过程包含的happens-before关系可以分为三类:
 * 1). 根据程序次序规则, 1 happens before 2; 2 happens before 3; 4 happens before 5; 5 happens before 6;
 * 2). 根据监视器锁规则, 3 happens before 4;
 * 3). 根据传递性规则, 2 happens before 5;
 * 这是什么意思呢? 我们可以看到第3条关系, 是由前两条推导出来的, 这完全符合happens-before原则; 根据第3条关系,
 * 回到MonitorDemo代码中, 我们可以看到, 2 happens before 5, 也就是说, a++这条写入是保证对int i = a可见的;
 * 也就是说, 在线程A释放了锁之后, 随后线程B获取同一个锁, 线程A在释放锁之前所有可见的共享变量, 在线程B获取同一个锁之后,
 * 都将立刻变得对线程B可见;
 *
 * 锁释放-获取的内存语义:
 * 1. 释放锁的内存语义如下:
 *    当线程释放锁时, JMM会把该线程对应的本地内存中的共享变量刷新到主内存中;
 * 2. 获取锁的内存语义如下:
 *    当线程获取锁时, JMM会把该线程对应的本地内存置为无效, 从而使得被监视器保护的临界区代码必须要从主内存中去读取共享变量;
 * 对比锁释放-获取的内存语义与volatile写-读的内存语义, 可以看出, 锁释放与volatile写有相同的内存语义,
 * 锁获取与volatile读有相同的内存语义; 下面对锁释放和锁获取的内存语义做个总结:
 * 1). 线程A释放一个锁, 实质上是线程A向接下来将要获取这个锁的某个线程发出了(线程A对共享变量所做修改的)消息;
 * 2). 线程B获取一个锁, 实质上是线程B接收了之前某个线程发出的(在释放这个锁之前对共享变量所做修改的)消息;
 * 3). 线程A释放锁, 随后线程B获取这个锁, 这个过程实质上是线程A通过主内存向线程B发送消息;
 */

class MonitorDemo {
    int a = 0;

    public synchronized void writer() {  // 1
        a++;                             // 2
    }                                    // 3

    public synchronized void reader() {  // 4
        int i = a;                       // 5
    }                                    // 6
}

public class LockPrinciple {
    public static void main(String[] args) {
    }
}
