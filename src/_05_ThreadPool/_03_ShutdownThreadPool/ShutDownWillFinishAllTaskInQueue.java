package _05_ThreadPool._03_ShutdownThreadPool;

/*
 * ref：https://blog.csdn.net/m0_46455412/article/details/109074772
 *
 * 代码验证线程池的入队过程以及shutdown()之后会不会处理之前提交过的任务。
 * 前三个任务丢进去，让他睡1s才开始执行，之后的任务正常执行。当第十五个任务进去执行后，调用shutdown关闭线程池。
 * 运行结果：从第4个任务开始入队，直到队列满了之后，又开始创建非核心线程去执行任务（看第14个任务，就会发现线程数变多了，说明增加了一个非核心线程，此时的队列还是满的）直到第15个任务，
 * 也创建了新线程去执行，此时的线程数为5个了，多了两个非核心线程。
 * 我们再来看看，前面15个任务有没有被执行完成呢，去数一下就会发现，都被执行完成了。
 * 数一下"测试,线程编号-X完成了任务"，一共打印了15次，所以结论是，shutdown()会执行完队列中的任务。
 */

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ShutDownWillFinishAllTaskInQueue {
    private static class MyThreadFactory implements ThreadFactory {
        private final String namePrefix;
        private final AtomicInteger nextId = new AtomicInteger(1);

        public MyThreadFactory(String featureOfGroup) {
            this.namePrefix = featureOfGroup + ",线程编号-";
        }

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(null, r, namePrefix + nextId.getAndIncrement(), 0);
        }
    }

    public static void main(String[] args) {
        ThreadFactory threadFactory = new MyThreadFactory("测试");
        ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 6, 20,
                TimeUnit.SECONDS, new LinkedBlockingDeque<>(10),
                threadFactory, new ThreadPoolExecutor.AbortPolicy());
        for (int i = 1; i <= 20; i++) {
            if (i <= 3) {
                executor.submit(
                        () -> {
                            try {
                                Thread.sleep(1000);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            System.out.println(Thread.currentThread().getName() + "完成了任务");
                        }
                );
            } else {
                executor.submit(
                        () -> System.out.println(Thread.currentThread().getName() + "完成了任务")
                );
                if (i == 15) {
                    executor.shutdown();
                }
            }
            System.out.println("任务数量" + executor.getTaskCount());
            System.out.println("大约正在执行任务的线程数：" + executor.getActiveCount());
            System.out.println("队列：" + executor.getQueue().toString());
            System.out.println("完成任务的数目：" + executor.getCompletedTaskCount());
            System.out.println("----------------------------");
        }
        System.out.println("done.");
    }
}
