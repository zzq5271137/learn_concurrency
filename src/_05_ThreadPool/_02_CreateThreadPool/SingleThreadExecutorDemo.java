package _05_ThreadPool._02_CreateThreadPool;

/*
 * 演示SingleThreadExecutor线程池;
 *
 * 参数:
 * 1. 只有1个线程的线程池, corePoolSize和maximumPoolSize都是1,
 *    不回收线程(由于corePoolSize和maximumPoolSize相同);
 * 2. keepAliveTime为0;
 * 3. 使用无界队列(LinkedBlockingQueue);
 *
 * 这种线程池和newFixedThreadPool基本一样, 只不过把corePoolSize和maximumPoolSize都设置为了1,
 * 而且也是使用无界队列(LinkedBlockingQueue), 所以也会导致相同的问题, 即当请求数越来越多,
 * 且无法及时处理完毕的时候, 也就是请求堆积的时候, 会容易造成内存的大量占用, 可能会导致OOM异常;
 */

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SingleThreadExecutorDemo {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 1000; i++) {
            executorService.execute(new Task());
        }
    }
}
