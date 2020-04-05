package _01_EightCores._04_Core4_LifeCycle.SynchronizedDemos._05_SynchronizedProperties;

/*
 * synchronized关键字的锁的性质:
 * 0. 互斥锁
 * 1. 可重入锁
 * 2. 不可中断锁
 *
 * 什么是互斥锁:
 * 同一时刻只能有一个线程获得这个锁, 或者说只能有一个线程访问被这个锁保护的临界资源;
 *
 * 什么是不可中断:
 * 当我请求一个锁, 一旦这个锁已经被别的线程获得了, 如果我还想获得, 只能选择等待或者是阻塞,
 * 直到别的线程释放这个锁, 如果别人永远也不释放锁, 那么我只能永远等待下去;
 * 相比之下, Lock类可以拥有中断的能力, 第一点, 如果我觉得我等的时间太长了, 有权中断现在已经获取到锁的线程的执行;
 * 第二点, 如果我觉得我等的时间太长了不想再等了, 也可以选择退出;
 *
 * 什么是可重入: 可重入, 指的是同一线程的外层函数获得锁后, 内层函数可以直接再次获得该锁;
 * 可重入锁的好处: 避免死锁, 提高封装性;
 * 可重入锁的粒度: 线程而非调用;
 *
 * 可重入粒度测试:
 * 1. 情况1, 证明同一个方法是可重入的;
 * 2. 情况2, 证明可重入不要求是同一个方法;
 * 3. 情况3, 证明可重入不要求是同一个类中的(继承的情况);
 *
 * 此处演示情况1:
 * 证明同一个方法是可重入的, 递归调用本方法;
 */

public class SynchronizedReentrantSituation1 {
    int a = 0;

    private synchronized void func() {
        System.out.println("func, a = " + a + ", " + Thread.currentThread().getName());
        if (a == 0) {
            a++;
            func();
        }
    }

    public static void main(String[] args) {
        SynchronizedReentrantSituation1 instance = new SynchronizedReentrantSituation1();
        instance.func();
    }
}
