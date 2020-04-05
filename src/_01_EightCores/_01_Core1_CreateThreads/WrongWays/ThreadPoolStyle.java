package _01_EightCores._01_Core1_CreateThreads.WrongWays;

/*
 * 使用线程池的方式实现线程:
 * 本章讨论的是实现多线程的本质方法,
 * 使用线程池, 本质上也是构建Thread对象去实现线程,
 * 所以不能直接单独说使用线程池是实现线程的方法之一;
 */

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Task implements Runnable {
    @Override
    public void run() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName());
    }
}

public class ThreadPoolStyle {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            executorService.submit(new Task() {
            });
        }
    }
}
