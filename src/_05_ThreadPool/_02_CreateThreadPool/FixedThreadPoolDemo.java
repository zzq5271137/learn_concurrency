package _05_ThreadPool._02_CreateThreadPool;

/*
 * 演示FixedThreadPool线程池;
 *
 * 参数:
 * 1. corePoolSize和maximumPoolSize相同的线程池(为传入的参数值),
 *     不回收线程(由于corePoolSize和maximumPoolSize相同);
 * 2. keepAliveTime为0;
 * 3. 使用无界队列(LinkedBlockingQueue);
 */

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class Task implements Runnable {
    @Override
    public void run() {
        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "正在执行");
    }
}

public class FixedThreadPoolDemo {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        for (int i = 0; i < 1000; i++) {
            executorService.execute(new Task());
        }
    }
}
