package _06_ThreadLocal._01_HowToUse;

/*
 * 演示很多个打印日期的任务(使用线程池);
 *
 * 在这个例子中, 我们将SimpleDateFormat对象作为一个静态属性去创建, 这样的话, 整个程序运行,
 * 就只需要创建一个SimpleDateFormat对象了, 所有的线程池中的线程都能够使用它;
 * 但当我们运行程序后发现, 程序的运行结果并不如我们预期的那样, 它打印出了一些相同的日期,
 * 但是我们的1000个日期打印任务应该打印出不同的日期; 原因在于, SimpleDateFormat并不是线程安全的,
 * 而且date()方法我们也没有去做同步, 所以当多个线程同时执行data()方法时, 发生了线程安全问题;
 */

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadLocalNormalUsage03 {
    private static ExecutorService threadPool = Executors.newFixedThreadPool(10);
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS z Z");

    public String date(int seconds) {
        // Date的这个构造方法传入的是毫秒, 从1970.01.01 00:00:00 GMT开始计算, 算出一个日期然后返回
        Date date = new Date(1000 * seconds);
        return sdf.format(date);
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 1000; i++) {
            final int finalI = i;
            threadPool.submit(new Runnable() {
                @Override
                public void run() {
                    System.out.println(new ThreadLocalNormalUsage03().date(finalI));
                }
            });
        }
        threadPool.shutdown();
    }
}
