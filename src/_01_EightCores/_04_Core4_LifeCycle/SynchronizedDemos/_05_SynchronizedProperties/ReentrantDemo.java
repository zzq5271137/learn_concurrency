package _01_EightCores._04_Core4_LifeCycle.SynchronizedDemos._05_SynchronizedProperties;

/*
 * 参考: https://blog.csdn.net/mulinsen77/article/details/88635558
 *      https://www.cnblogs.com/incognitor/p/9894604.html
 *
 * 可重入锁的定义:
 * 若一个程序或子程序可以"在任意时刻被中断然后操作系统调度执行另外一段代码, 这段代码又调用了该子程序不会出错",
 * 则称其为可重入(reentrant或re-entrant)的; 即当该子程序正在运行时, 执行线程可以再次进入并执行它,
 * 仍然获得符合设计时预期的结果; 与多线程并发执行的线程安全不同, 可重入强调对单个线程执行时重新进入同一个子程序仍然是安全的;
 *
 * 通俗来说:
 * 当线程请求一个由其它线程持有的对象锁时, 该线程会阻塞, 而当线程请求由自己持有的对象锁时, 如果该锁是重入锁, 请求就会成功, 否则阻塞;
 *
 * synchronized是可重入锁, 之后要讲的Lock接口的一个实现类ReentrantLock也是可重入锁;
 *
 * 在下面的例子中, 这里的对象锁只有一个, 就是child对象的锁, 当执行child.doSomething时, 该线程获得child对象的锁,
 * 在doSomething方法内执行doAnotherThing时再次请求child对象的锁, 因为synchronized是重入锁, 所以可以得到该锁,
 * 继续在doAnotherThing里执行父类的doSomething方法时第三次请求child对象的锁, 同样可得到;
 * 如果不是重入锁的话, 那后面这两次请求锁将会被一直阻塞, 从而导致死锁;
 * 所以在java内部, 同一线程在调用自己类中其他synchronized方法/块或调用父类的synchronized方法/块都不会阻碍该线程的执行;
 * 就是说同一线程对同一个对象锁是可重入的, 而且同一个线程可以获取同一把锁多次, 也就是可以多次重入;
 * 因为java线程是基于"每线程(per-thread)", 而不是基于"每调用(per-invocation)"的
 * (java中线程获得对象锁的操作是以线程为粒度的, per-invocation互斥体获得对象锁的操作是以每调用作为粒度的);
 *
 * 可重入锁实现可重入性原理或机制是:
 * 每一个锁关联一个线程持有者和计数器, 当计数器为0时表示该锁没有被任何线程持有, 那么任何线程都可能获得该锁而调用相应的方法;
 * 当某一线程请求成功后, JVM会记下锁的持有线程, 并且将计数器置为1; 此时其它线程请求该锁, 则必须等待;
 * 而该持有锁的线程如果再次请求这个锁, 就可以再次拿到这个锁, 同时计数器会递增;
 * 当线程退出同步代码块时, 计数器会递减, 如果计数器为0, 则释放该锁;
 */

class FatherClass {
    public synchronized void doSomething() {
        System.out.println("father.doSomething: " + Thread.currentThread().getName());
    }
}

class ChildClass extends FatherClass {
    @Override
    public synchronized void doSomething() {
        System.out.println("child.doSomething: " + Thread.currentThread().getName());
        doAnotherThing();
    }

    private synchronized void doAnotherThing() {
        super.doSomething();
        System.out.println("child.doAnotherThing: " + Thread.currentThread().getName());
    }
}

public class ReentrantDemo {
    public static void main(String[] args) {
        ChildClass child = new ChildClass();
        child.doSomething();
    }
}
