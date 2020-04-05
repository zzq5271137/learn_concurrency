package _01_EightCores._05_Core5_CoreMethodsInThreadAndObjectClass;

/*
 * 演示join()期间被中断的效果;
 *
 * 当主线程等待子线程join()期间被中断了, 我们最好在catch里去把子线程也中断了, 这样可以避免脏数据的产生;
 */

import java.util.concurrent.TimeUnit;

public class JoinInterrupt {
    public static void main(String[] args) {
        Thread.currentThread().setName("MainThread");
        Thread mainThread = Thread.currentThread();
        Thread childThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(2);
                    mainThread.interrupt(); // 中断主线程
                    TimeUnit.SECONDS.sleep(5);
                    System.out.println(Thread.currentThread().getName() + "执行完毕");
                } catch (InterruptedException e) {
                    System.out.println(Thread.currentThread().getName() + "被中断");
                    e.printStackTrace();
                }
            }
        }, "ChildThread");
        childThread.start();

        System.out.println(Thread.currentThread().getName() + "等待子线程运行完毕");
        try {
            childThread.join();
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + "等待子线程join()期间被中断");
            e.printStackTrace();

            // 为了不造成脏数据, 当子线程join()被打断, 应该中断子线程
            childThread.interrupt();
        }
        System.out.println(Thread.currentThread().getName() + "执行完毕");
    }
}
