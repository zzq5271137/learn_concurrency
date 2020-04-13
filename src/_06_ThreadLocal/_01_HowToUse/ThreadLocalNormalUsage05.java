package _06_ThreadLocal._01_HowToUse;

/*
 * 演示很多个打印日期的任务(使用线程池);
 *
 * 使用ThreadLocal解决ThreadLocalNormalUsage05中的性能问题; 使用ThreadLocal,
 * 让每一个线程有一个自己的DateFormat对象, 这样既没有线程安全问题, 又解决了性能问题;
 */

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class ThreadDateFormatter {
    private static ThreadLocal<SimpleDateFormat> dateFormThreadLocal =
            new ThreadLocal<SimpleDateFormat>() {
                @Override
                protected SimpleDateFormat initialValue() {
                    return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS z Z");
                }
            };

    // 使用lambda表达式(与上面的创建方式效果一样, 只是写法不同)
    private static ThreadLocal<SimpleDateFormat> dateFormThreadLocal2 =
            ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS z Z"));

    public static SimpleDateFormat get() {
        return dateFormThreadLocal2.get();
    }
}

public class ThreadLocalNormalUsage05 {
    private static ExecutorService threadPool = Executors.newFixedThreadPool(10);

    public String date(int seconds) {
        // Date的这个构造方法传入的是毫秒, 从1970.01.01 00:00:00 GMT开始计算, 算出一个日期然后返回
        Date date = new Date(1000 * seconds);
        SimpleDateFormat sdf = ThreadDateFormatter.get();

        // 使用System.identityHashCode()获取对象的真实地址
        System.out.println(Thread.currentThread().getName() + ": " + System.identityHashCode(sdf));

        return sdf.format(date);
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 1000; i++) {
            final int finalI = i;
            threadPool.submit(new Runnable() {
                @Override
                public void run() {
                    System.out.println(new ThreadLocalNormalUsage05().date(finalI));
                }
            });
        }
        threadPool.shutdown();
    }
}
