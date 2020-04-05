package _01_EightCores._05_Core5_CoreMethodsInThreadAndObjectClass;

/*
 * join()期间的线程状态:
 * 1. 不带参数的join(), 是为WAITING;
 * 2. 带参数的join(), 是TIMED_WAITING;
 *
 * 可以通过两种方式查看:
 * 1. 通过代码的方式: mainThread.getState();
 * 2. 通过Debugger看线程的状态;
 */

import java.util.concurrent.TimeUnit;

public class JoinThreadState {
    public static void main(String[] args) {
        Thread.currentThread().setName("MainThread");
        Thread mainThread = Thread.currentThread();
        Thread childThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println(Thread.currentThread().getName() + "开始运行");
                    TimeUnit.SECONDS.sleep(3);
                    System.out.println("当前主线程状态为: " + mainThread.getState());
                    TimeUnit.SECONDS.sleep(3);
                    System.out.println(Thread.currentThread().getName() + "运行完毕");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "ChildThread");
        childThread.start();

        System.out.println(Thread.currentThread().getName() + "等待子线程运行完毕");
        try {
            childThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "运行完毕");
    }
}
