package _12_FlowControl._03_Semaphore;

/*
 * 演示Semaphore的基本用法;
 */

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphoreDemo {
    static Semaphore semaphore = new Semaphore(6, true);  // 6个许可证，下面的task一次获取3个，所以同时最多有2个线程进入临界区执行

    static class Task implements Runnable {
        @Override
        public void run() {
            try {
                // semaphore.acquire();
                semaphore.acquire(3);  // 一次获取多个许可证
                try {
                    System.out.println(Thread.currentThread().getName() + "获取到许可证, 开始执行任务...");
                    TimeUnit.SECONDS.sleep(2);
                } finally {
                    System.out.println(Thread.currentThread().getName() + "任务执行完成, 释放许可证");
                    // semaphore.release();
                    semaphore.release(3);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(50);
        for (int i = 0; i < 100; i++) {
            service.submit(new Task());
        }
        service.shutdown();
    }
}
