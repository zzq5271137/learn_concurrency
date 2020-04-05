package _01_EightCores._04_Core4_LifeCycle.SynchronizedDemos._02_HowToUse;

/*
 * 对象锁示例之实例方法形式:
 * synchronized关键字加在实例方法上, 锁对象是这个方法所在的类的当前实例对象(即调用这个方法的对象)
 */

public class SynchronizedObjectMethod implements Runnable {
    static SynchronizedObjectMethod instance = new SynchronizedObjectMethod();

    private synchronized void func() throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + "进入func");
        Thread.sleep(3000);
        System.out.println(Thread.currentThread().getName() + "离开func");
    }

    @Override
    public void run() {
        try {
            func();
        } catch (InterruptedException e) {
            e.printStackTrace();
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
