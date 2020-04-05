package _01_EightCores._05_Core5_CoreMethodsInThreadAndObjectClass.Examples;

/*
 * 两个线程交替打印0~100, 使用synchronized关键字实现
 */

public class PrintOddEvenUsingSynchronized {
    private static final Object LOCK = new Object();
    private static int count = 0;

    public static void main(String[] args) {
        new Thread(new Runnable() {
            /**
             * 只打印偶数的线程
             */
            @Override
            public void run() {
                while (count < 100) {
                    synchronized (LOCK) {
                        // 使用位运算来判断奇偶, 效率更高
                        if ((count & 1) == 0) {
                            System.out.println(Thread.currentThread().getName() + ": " + (count++));
                        }
                    }
                }
            }
        }, "偶数线程").start();

        new Thread(new Runnable() {
            /**
             * 只打印奇数的线程
             */
            @Override
            public void run() {
                while (count < 100) {
                    synchronized (LOCK) {
                        // 使用位运算来判断奇偶, 效率更高
                        if ((count & 1) == 1) {
                            System.out.println(Thread.currentThread().getName() + ": " + (count++));
                        }
                    }
                }
            }
        }, "奇数线程").start();
    }
}
