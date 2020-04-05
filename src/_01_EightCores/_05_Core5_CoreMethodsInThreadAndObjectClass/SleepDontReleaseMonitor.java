package _01_EightCores._05_Core5_CoreMethodsInThreadAndObjectClass;

/*
 * Thread类中的sleep()方法:
 * 和wait()方法不同, sleep()方法不会释放锁, 无论是synchronized关键字的monitor锁还是Lock类的锁, 都不会释放;
 *
 * 此处演示sleep()方法不释放synchronized关键字的monitor锁;
 */

public class SleepDontReleaseMonitor implements Runnable {
    @Override
    public void run() {
        synchronized (this) {
            System.out.println(Thread.currentThread().getName() + "获取到monitor锁");
            try {
                System.out.println(Thread.currentThread().getName() + "开始休眠");
                Thread.sleep(5000);
                System.out.println(Thread.currentThread().getName() + "休眠结束");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "执行结束, 释放monitor锁");
        }
    }

    public static void main(String[] args) {
        Runnable runnable = new SleepDontReleaseMonitor();
        new Thread(runnable).start();
        new Thread(runnable).start();
    }
}
