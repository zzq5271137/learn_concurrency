package _06_ThreadLocal._01_HowToUse;

/*
 * 演示很多个打印日期的任务(使用线程池);
 *
 * 在这个例子中, 针对ThreadLocalNormalUsage03.java中的线程安全问题, 我们使用加锁的方式去解决;
 * 以加锁的方式的确可以解决线程安全问题, 但是以这种方式, 效率会很低, 因为这1000个线程需要逐个去执行完成data()方法;
 * 这显然也不是最佳的解决方案;
 */

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadLocalNormalUsage04 {
    private static ExecutorService threadPool = Executors.newFixedThreadPool(10);
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS z Z");

    public void date(int seconds) {
        // Date的这个构造方法传入的是毫秒, 从1970.01.01 00:00:00 GMT开始计算, 算出一个日期然后返回
        Date date = new Date(1000 * seconds);
        String formatDate;
        synchronized (ThreadLocalNormalUsage04.class) {
            formatDate = sdf.format(date);
        }

        // 使用System.identityHashCode()获取对象的真实地址
        System.out.println(Thread.currentThread().getName() + ": "
                + System.identityHashCode(sdf) + ", " + formatDate);
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 1000; i++) {
            final int finalI = i;
            threadPool.submit(new Runnable() {
                @Override
                public void run() {
                    new ThreadLocalNormalUsage04().date(finalI);
                }
            });
        }
        threadPool.shutdown();
    }
}
