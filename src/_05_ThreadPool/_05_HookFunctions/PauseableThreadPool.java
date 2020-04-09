package _05_ThreadPool._05_HookFunctions;

/*
 * 每个任务在执行的前后都可以放钩子函数;
 */

import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PauseableThreadPool extends ThreadPoolExecutor {

    public PauseableThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime,
                               TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    public PauseableThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime,
                               TimeUnit unit, BlockingQueue<Runnable> workQueue,
                               ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }

    public PauseableThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime,
                               TimeUnit unit, BlockingQueue<Runnable> workQueue,
                               RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
    }

    public PauseableThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime,
                               TimeUnit unit, BlockingQueue<Runnable> workQueue,
                               ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    private volatile boolean isPaused;
    private final ReentrantLock LOCK = new ReentrantLock();
    private Condition unpaused = LOCK.newCondition();

    /**
     * 使线程池暂停的方法(停止任务的执行)
     */
    public void pause() {
        LOCK.lock();
        try {
            isPaused = true;
        } finally {
            LOCK.unlock();
        }
    }

    /**
     * 使线程池恢复运行的方法(恢复任务的执行)
     */
    public void resume() {
        LOCK.lock();
        try {
            isPaused = false;
            unpaused.signalAll();
        } finally {
            LOCK.unlock();
        }
    }

    /**
     * 在每个任务执行前执行的钩子函数
     */
    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
        LOCK.lock();
        try {
            while (isPaused) {
                unpaused.await();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            LOCK.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        PauseableThreadPool threadPool = new PauseableThreadPool(10, 20, 10L,
                TimeUnit.SECONDS, new LinkedBlockingDeque<>());
        Runnable task = new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + "正在执行");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        for (int i = 0; i < 10000; i++) {
            threadPool.execute(task);
        }
        Logger logger = Logger.getAnonymousLogger();
        Thread.sleep(1500);
        threadPool.pause();
        logger.log(Level.WARNING, "线程池被暂停了, 3秒后继续运行...");
        Thread.sleep(3000);
        logger.log(Level.INFO, "线程池恢复运行");
        threadPool.resume();
    }
}
