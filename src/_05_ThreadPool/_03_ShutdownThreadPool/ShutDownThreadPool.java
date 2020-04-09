package _05_ThreadPool._03_StopThreadPool;

/*
 * 停止线程池的相关方法:
 * 1. shutdown()方法
 *    调用这个方法, 并不会立即停止线程池, 它只是启动了整个关闭过程; 因为当线程池执行到一半的时候,
 *    线程中有正在执行的任务, 队列中也存储着等待被执行的任务, 所以线程池并不是立即停止的;
 *    当调用了这个方法, 线程池会把正在执行的任务以及队列中等待的任务都执行完, 再去停止线程池,
 *    并且在这段期间, 线程池将会拒绝新提交的任务(抛出RejectedExecutionException异常);
 * 2. isShutdown()方法
 *    返回线程池是否已经停止(这里的停止不是指完全停止, 而是指线程池是否已经进入停止状态了);
 * 3. isTerminated()方法
 *    返回线程池是否已经完全终止了;
 */

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;


class ShutDownThreadPoolTask implements Runnable {
    @Override
    public void run() {
        try {
            Thread.sleep(500);
            System.out.println(Thread.currentThread().getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class ShutDownThreadPool {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 70; i++) {
            executorService.execute(new ShutDownThreadPoolTask());
        }
        Thread.sleep(1500);

        /*
         * 当调用了shutdown()方法, 线程池会把正在执行的任务以及队列中等待的任务都执行完, 再去停止线程池,
         * 并且在这段期间, 线程池将会拒绝新提交的任务(抛出RejectedExecutionException异常);
         */
        System.out.println("还未调用shutdown()前, isShutdown(): " + executorService.isShutdown());
        executorService.shutdown();
        System.out.println("调用shutdown()后, isShutdown(): " + executorService.isShutdown());
        try {
            executorService.execute(new ShutDownThreadPoolTask());  // 会被拒绝
        } catch (RejectedExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("等待5秒前, isTerminated(): " + executorService.isTerminated());
        Thread.sleep(5000);
        System.out.println("等待5秒后, isTerminated(): " + executorService.isTerminated());
    }
}
