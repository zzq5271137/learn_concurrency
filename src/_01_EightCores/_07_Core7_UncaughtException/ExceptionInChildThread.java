package _01_EightCores._07_Core7_UncaughtException;

/*
 * 在单线程程序中, 如果有未捕获的异常, 程序会抛出异常并打印出堆栈信息到控制台, 然后程序停止;
 * 在多线程程序中, 子线程中如果有未捕获的异常, 也是会打印出堆栈信息然后子线程停止, 但是主线程不受影响;
 * 也就是说, 子线程打印的堆栈信息会淹没在主线程的正常日志信息中, 我们很有可能会忽略子线程的异常堆栈信息;
 */

public class ExceptionInChildThread implements Runnable {
    @Override
    public void run() {
        throw new RuntimeException();
    }

    public static void main(String[] args) {
        new Thread(new ExceptionInChildThread()).start();
        for (int i = 0; i < 1000; i++) {
            System.out.println(i);
        }
    }
}
