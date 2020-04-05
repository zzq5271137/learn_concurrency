package _01_EightCores._05_Core5_CoreMethodsInThreadAndObjectClass;

/*
 * 展示wait()和notify()的基本用法:
 * 1. 研究代码的执行顺序;
 * 2. 证明wait()是释放锁的;
 *
 * wait()、notify()、notifyAll()都是属于Object类的方法, 而且都是native(即具体实现是在JVM层)和final(即不可重写)的方法;
 * 使用他们其实是相对底层的用法, 当前JDK其实已经封装好了类(Condition类)用来实现类似功能;
 *
 * 注意, 执行wait()方法的, 一定是一个对象, 哪一个对象执行wait()方法就代表释放哪一把锁;
 * 如果wait()方法前不加对象(即直接写wait(), 而不是resource.wait()), 就代表是this.wait(),
 * 即, 释放执行当前方法的当前实例对象的锁, 当然前提是得先获得这把锁(即, 当前的synchronized方法/代码块的确是由这把锁守护的);
 *
 * 不要使用Thread的实例对象作为锁(也即, 不要使用thread.wait()):
 * 具体来说, 如果一个类继承了Thread类, 并且需要实际运行它(即需要创建一个它的实例, 以下简称myThread, 然后需要执行myThread.start()),
 * 就不要把myThread用作锁, 因为在myThread的run()方法执行结束后, 会自动调用自身的notifyAll()方法(即this.notifyAll()),
 * 也就是说, 在myThread的run()方法执行结束后, 会自动地去唤醒正在等待myThread这把锁的所有线程(这里的等待是指执行过myThread.wait()),
 * 这显然会打乱我们的线程执行的设计规划, 会得到我们不想要的结果;
 * 详见DontUseThreadInstanceAsLock.java;
 */

public class WaitAndNotifyIntro {
    public final static Object LOCK = new Object();

    static class Thread1 extends Thread {
        @Override
        public void run() {
            synchronized (LOCK) {
                System.out.println(Thread.currentThread().getName() + "(Thread1类)获取到锁, 开始执行");
                try {
                    Thread.sleep(2000);
                    System.out.println(Thread.currentThread().getName() + "(Thread1类)执行wait(), 释放锁");
                    LOCK.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "(Thread1类)被唤醒, 重新获取锁");
            }
        }
    }

    static class Thread2 extends Thread {
        @Override
        public void run() {
            synchronized (LOCK) {
                System.out.println(Thread.currentThread().getName() + "(Thread2类)获取到锁, 开始执行");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                LOCK.notify();
                System.out.println(Thread.currentThread().getName() + "(Thread2类)调用了notify()");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "(Thread2类)执行结束, 释放锁");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread1 t1 = new Thread1();
        Thread2 t2 = new Thread2();
        t1.start();
        Thread.sleep(200);
        t2.start();
    }
}
