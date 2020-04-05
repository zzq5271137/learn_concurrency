package _01_EightCores._07_Core7_UncaughtException;

/*
 * 设置全局未捕获异常处理器;
 */

import java.util.concurrent.TimeUnit;

public class UseMyUncaughtExceptionHandler implements Runnable {
    @Override
    public void run() {
        int num = 1 / 0;
        System.out.println(num);
    }

    public static void main(String[] args) throws InterruptedException {
        Thread.setDefaultUncaughtExceptionHandler(new MyUncaughtExceptionHandler("捕获器"));

        new Thread(new UseMyUncaughtExceptionHandler(), "MyThread-1").start();
        TimeUnit.MICROSECONDS.sleep(300);
        new Thread(new UseMyUncaughtExceptionHandler(), "MyThread-2").start();
        TimeUnit.MICROSECONDS.sleep(300);
        new Thread(new UseMyUncaughtExceptionHandler(), "MyThread-3").start();
        TimeUnit.MICROSECONDS.sleep(300);
        new Thread(new UseMyUncaughtExceptionHandler(), "MyThread-4").start();
    }
}
