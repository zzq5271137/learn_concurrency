package _01_EightCores._05_Core5_CoreMethodsInThreadAndObjectClass;

/*
 * 展示wait()只释放当前的那把锁(即执行wait()方法的对象的锁);
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

public class WaitOnlyReleaseOwnMonitor {
    private static final Object RESOURCE_A = new Object();
    private static final Object RESOURCE_B = new Object();

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (RESOURCE_A) {
                    System.out.println(Thread.currentThread().getName() + "获得RESOURCE_A锁");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (RESOURCE_B) {
                        System.out.println(Thread.currentThread().getName() + "获得RESOURCE_B锁");
                        try {
                            Thread.sleep(2000);
                            System.out.println(Thread.currentThread().getName() + "执行RESOURCE_A.wait(), 释放RESOURCE_A锁");
                            RESOURCE_A.wait(); // 只释放了RESOURCE_A锁
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + "尝试获得RESOURCE_A锁");
                synchronized (RESOURCE_A) {
                    System.out.println(Thread.currentThread().getName() + "获得RESOURCE_A锁");
                    System.out.println(Thread.currentThread().getName() + "尝试获得RESOURCE_B锁");
                    synchronized (RESOURCE_B) {
                        System.out.println(Thread.currentThread().getName() + "获得RESOURCE_B锁");
                    }
                }
            }
        });

        t1.start();
        Thread.sleep(200);
        t2.start();
    }
}
