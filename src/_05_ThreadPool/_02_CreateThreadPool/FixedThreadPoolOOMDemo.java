package _05_ThreadPool._02_CreateThreadPool;

/*
 * 演示FixedThreadPool线程池的OOM异常的情况;
 *
 * 通过观察Executors.newFixedThreadPool()的源码我们可以知道, 这种线程池使用的是LinkedBlockingQueue,
 * 这是无界队列; 由于LinkedBlockingQueue是没有容量上限的, 所以当请求数越来越多, 并且无法及时处理完毕的时候,
 * 也就是请求堆积的时候, 会容易造成内存的大量占用, 可能会导致OOM异常;
 */

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class SubThread implements Runnable {
    @Override
    public void run() {
        try {
            TimeUnit.SECONDS.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class FixedThreadPoolOOMDemo {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            executorService.execute(new SubThread());
        }
    }
}
