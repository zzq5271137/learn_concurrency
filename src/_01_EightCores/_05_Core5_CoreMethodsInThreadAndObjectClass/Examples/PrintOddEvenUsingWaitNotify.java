package _01_EightCores._05_Core5_CoreMethodsInThreadAndObjectClass.Examples;

/*
 * 两个线程交替打印0~100, 使用wait()和notify()实现
 */

public class PrintOddEvenUsingWaitNotify {
    private static final Object LOCK = new Object();
    private static int count = 0;

    static class TurningRunner implements Runnable {
        @Override
        public void run() {
            while (count <= 100) {
                synchronized (LOCK) {
                    System.out.println(Thread.currentThread().getName() + ": " + (count++));
                    LOCK.notify();
                    if (count <= 100) {
                        try {
                            LOCK.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new Thread(new TurningRunner(), "偶数线程").start();
        Thread.sleep(100);
        new Thread(new TurningRunner(), "奇数线程").start();
    }
}
