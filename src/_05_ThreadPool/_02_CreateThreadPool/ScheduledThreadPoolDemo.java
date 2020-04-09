package _05_ThreadPool._02_CreateThreadPool;

/*
 * 演示ScheduledThreadPool线程池;
 *
 * 参数:
 * 支持定时以及周期性任务执行的线程池,
 * 1. corePoolSize为传入的参数值, maximumPoolSize为Integer.MAX_VALUE(无界线程池);
 * 2. keepAliveTime为0, 立即回收线程(因为corePoolSize和maximumPoolSize大小不一样, 而且keepAliveTime为0,
 *    所以当线程数超过corePoolSize且某个线程空闲了, 就立即终止/回收它);
 * 3. 使用延迟队列(DelayedWorkQueue), 有延迟任务执行的功能;
 */

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPoolDemo {
    public static void main(String[] args) {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(10);
        executorService.schedule(new Task(), 3, TimeUnit.SECONDS);
        executorService.scheduleAtFixedRate(new Task(), 5, 2, TimeUnit.SECONDS);
    }
}
