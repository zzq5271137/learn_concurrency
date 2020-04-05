package _01_EightCores._04_Core4_LifeCycle.SynchronizedDemos._05_SynchronizedProperties;

/*
 * 可重入粒度测试:
 * 1. 情况1, 证明同一个方法是可重入的;
 * 2. 情况2, 证明可重入不要求是同一个方法;
 * 3. 情况3, 证明可重入不要求是同一个类中的(继承的情况);
 *
 * 此处演示情况3:
 * 证明可重入不要求是同一个类中的(继承的情况), 子类调用父类中的synchronized方法;
 */

class SuperClass {
    public synchronized void func() {
        System.out.println("super.func: " + Thread.currentThread().getName());
    }
}

class SubClass extends SuperClass {
    @Override
    public synchronized void func() {
        System.out.println("sub.func: " + Thread.currentThread().getName());
        super.func();
    }
}

public class SynchronizedReentrantSituation3 {
    public static void main(String[] args) {
        SubClass subClass = new SubClass();
        subClass.func();
    }
}
