package _01_EightCores._05_Core5_CoreMethodsInThreadAndObjectClass;

/*
 * Thread.currentThread()返回的是正在执行当前代码的线程实例的引用;
 */

public class CurrentThreadDemo implements Runnable {
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName());
    }

    public static void main(String[] args) {
        Runnable runnable = new CurrentThreadDemo();

        // 打印main, 因为是在主线程中直接执行这个方法(跟线程就没关系了)
        runnable.run();

        // 打印Thread-1
        new Thread(runnable, "Thread-1").start();

        // 打印Thread-2
        new Thread(runnable, "Thread-2").start();
    }
}
