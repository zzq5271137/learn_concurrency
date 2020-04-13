package _06_ThreadLocal._01_HowToUse;

/*
 * 演示很多个打印日期的任务(使用线程池);
 *
 * 在这个例子中, 我们用容量为10的FixedThreadPool去执行1000个打印日期的任务, 也就是说每个线程执行100个任务;
 * 但是, 我们在每一次执行date()方法时都会创建一个新的SimpleDateFormat对象, 所以, 这里一共创建了1000个,
 * 每个线程池中的线程创建了100个; 这样其实是很浪费资源的;
 */

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadLocalNormalUsage02 {
    public static ExecutorService threadPool = Executors.newFixedThreadPool(10);

    public void date(int seconds) {
        // Date的这个构造方法传入的是毫秒, 从1970.01.01 00:00:00 GMT开始计算, 算出一个日期然后返回
        Date date = new Date(1000 * seconds);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS z Z");

        // 使用System.identityHashCode()获取对象的真实地址
        System.out.println(Thread.currentThread().getName() + ": "
                + System.identityHashCode(sdf) + ", " + sdf.format(date));
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 1000; i++) {
            final int finalI = i;
            threadPool.submit(new Runnable() {
                @Override
                public void run() {
                    new ThreadLocalNormalUsage02().date(finalI);
                }
            });
        }
        threadPool.shutdown();
    }
}
