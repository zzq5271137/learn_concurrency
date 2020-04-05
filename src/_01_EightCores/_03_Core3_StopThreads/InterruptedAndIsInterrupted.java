package _01_EightCores._03_Core3_StopThreads;

/*
 * static boolean interrupted()和boolean isInterrupted()的区别:
 * 1. isInterrupted()会返回线程是否中断, interrupted()不仅会返回线程是否中断, 还会清除线程的中断状态;
 * 2. interrupted()是静态的, 直接通过Thread.interrupted()调用;
 * 3. interrupted()的目标对象是"当前线程"(即执行这个方法的线程), 而不管本方法的调用来自哪个对象(详见下面的例子);
 */

public class InterruptedAndIsInterrupted {
    public static void main(String[] args) throws InterruptedException {
        Thread threadOne = new Thread(new Runnable() {
            @Override
            public void run() {
                for (; ; ) {
                }
            }
        });

        threadOne.start();
        threadOne.interrupt();
        System.out.println("isInterrupted(): " + threadOne.isInterrupted());
        System.out.println("interrupted(): " + threadOne.interrupted());
        System.out.println("interrupted(): " + Thread.interrupted());
        System.out.println("isInterrupted(): " + threadOne.isInterrupted());
        threadOne.join();
        System.out.println("Main thread is over.");
    }
}
