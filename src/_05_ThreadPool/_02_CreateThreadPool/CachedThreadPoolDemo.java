package _05_ThreadPool._02_CreateThreadPool;

/*
 * 演示CachedThreadPoolDemo线程池;
 *
 * 可缓存的线程池, 因为它设置了keepAliveTime(默认是60秒), 所以会把闲置的线程给回收回来,
 * 是无界线程池, 因为它的maximumPoolSize值为Integer.MAX_VALUE,
 * 使用直接交接队列(SynchronousQueue), 即提交的任务直接交给线程执行;
 *
 * 这种线程池的弊端在于maximumPoolSize被设置为了Integer.MAX_VALUE, 这可能会创建数量非常多的线程,
 * 甚至导致OOM异常;
 */

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CachedThreadPoolDemo {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 1000; i++) {
            executorService.execute(new Task());
        }
    }
}
