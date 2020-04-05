package _01_EightCores._04_Core4_LifeCycle.SynchronizedDemos._05_SynchronizedProperties;

/*
 * 可重入粒度测试:
 * 1. 情况1, 证明同一个方法是可重入的;
 * 2. 情况2, 证明可重入不要求是同一个方法;
 * 3. 情况3, 证明可重入不要求是同一个类中的(继承的情况);
 *
 * 此处演示情况2:
 * 证明可重入不要求是同一个方法, 调用类内部其他的synchronized方法;
 */

public class SynchronizedReentrantSituation2 {

    private synchronized void func1() {
        System.out.println("func1: " + Thread.currentThread().getName());
        func2();
    }

    private synchronized void func2() {
        System.out.println("func2: " + Thread.currentThread().getName());
    }

    public static void main(String[] args) {
        SynchronizedReentrantSituation2 instance = new SynchronizedReentrantSituation2();
        instance.func1();
    }
}
