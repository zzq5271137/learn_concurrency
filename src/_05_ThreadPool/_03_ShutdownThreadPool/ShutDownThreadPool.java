package _05_ThreadPool._03_ShutdownThreadPool;

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
 * 4. awaitTermination(timeout, TimeUnit)方法
 *    这个方法不是停止线程池的方法, 当调用这个方法后, 在timeout长度的时间内, 如果线程池完全停止(注意是完全停止),
 *    就会返回true, 否则返回false; 执行这个方法的线程在等待timeout期间会陷入阻塞状态;
 * 5. shutdownNow()方法
 *    立即停止线程池的方法; 当调用此方法后, 对于正在执行的任务, 线程池会向每一个正在执行的任务发送interrupt()请求,
 *    而对于队列中的任务, 会被放进一个列表, 然后作为返回值返回给调用方(这个方法是有返回值的, 是一个列表);
 */

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;


class ShutDownThreadPoolTask implements Runnable {
    @Override
    public void run() {
        try {
            Thread.sleep(500);
            System.out.println(Thread.currentThread().getName() + "正在执行");
        } catch (InterruptedException e) {
            System.err.println(Thread.currentThread().getName() + "被中断了");
        }
    }
}

public class ShutDownThreadPool {
    static ExecutorService executorService = Executors.newFixedThreadPool(10);

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            executorService.execute(new ShutDownThreadPoolTask());
        }
        Thread.sleep(1500);

        // testShutdown();
        // testAwaitTermination();
        // testShutdownNow();
    }

    private static void testShutdown() throws InterruptedException {
        System.out.println("调用shutdown()前, isShutdown(): " + executorService.isShutdown());
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

    private static void testAwaitTermination() throws InterruptedException {
        System.out.println("调用shutdown()前, awaitTermination(): " + executorService.awaitTermination(1L, TimeUnit.SECONDS));
        executorService.shutdown();
        System.out.println("调用shutdown()后, awaitTermination(): " + executorService.awaitTermination(2L, TimeUnit.SECONDS));
        Thread.sleep(3000);
        System.out.println("等待3秒后, awaitTermination(): " + executorService.awaitTermination(1L, TimeUnit.SECONDS));

    }

    private static void testShutdownNow() {
        List<Runnable> tasksInQueue = executorService.shutdownNow();
        System.out.println("还在队列中的任务数: " + tasksInQueue.size());
    }
}
