package _01_EightCores._07_Core7_UncaughtException;

/*
 * 子线程的异常不能用传统方法捕获;
 */

import java.util.concurrent.TimeUnit;

public class CantCatchChildThreadExceptionDirectly implements Runnable {
    @Override
    public void run() {
        throw new RuntimeException();
    }

    public static void main(String[] args) throws InterruptedException {
        /*
         * 这里, 其实根本就没有捕获到子线程内的异常;
         * 因为这里的try/catch是由主线程执行的, 它只能捕获主线程中的异常;
         * 下面的写法, 其实是在捕获诸如start()时、或者sleep()时可能发生的异常;
         * 而这四个子线程中的异常是捕获不到的, 所以也不会打印"Caught Exception.";
         */
        try {
            new Thread(new CantCatchChildThreadExceptionDirectly(), "MyThread-1").start();
            TimeUnit.MICROSECONDS.sleep(300);
            new Thread(new CantCatchChildThreadExceptionDirectly(), "MyThread-2").start();
            TimeUnit.MICROSECONDS.sleep(300);
            new Thread(new CantCatchChildThreadExceptionDirectly(), "MyThread-3").start();
            TimeUnit.MICROSECONDS.sleep(300);
            new Thread(new CantCatchChildThreadExceptionDirectly(), "MyThread-4").start();
        } catch (RuntimeException e) {
            System.out.println("Caught Exception.");
        }
    }
}
