package _01_EightCores._04_Core4_LifeCycle.SynchronizedDemos._04_OtherCases;

/*
 * 两个线程同一时刻访问同一个实例对象的普通同步方法和静态同步方法
 */

public class SynchronizedStaticAndNormalMethod implements Runnable {
    static SynchronizedStaticAndNormalMethod instance = new SynchronizedStaticAndNormalMethod();

    public static synchronized void func1() throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + "进入func1");
        Thread.sleep(3000);
        System.out.println(Thread.currentThread().getName() + "离开func1");
    }

    public synchronized void func2() throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + "进入func2");
        Thread.sleep(3000);
        System.out.println(Thread.currentThread().getName() + "离开func2");
    }

    @Override
    public void run() {
        if (Thread.currentThread().getName().equals("Thread-0")) {
            try {
                func1();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            try {
                func2();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(instance);
        Thread t2 = new Thread(instance);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("done");
    }
}
