package _15_SimpleProject._06_Computable5;

/*
 * 使用线程池和CountDownLatch测试缓存性能;
 */

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class ThreadSafeDateFormatter {
    private static ThreadLocal<SimpleDateFormat> sdf = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("mm:ss");
        }
    };

    public static SimpleDateFormat get() {
        return sdf.get();
    }
}

public class TestMyCacheUsingThreadPool {
    public static void main(String[] args) throws InterruptedException {
        MyCache myCache = new MyCache(new ExpensiveFunction());
        CountDownLatch latch = new CountDownLatch(1);
        ExecutorService service = Executors.newFixedThreadPool(100);
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            service.submit(() -> {
                Integer result = null;
                try {
                    System.out.println(Thread.currentThread().getName() + "抵达门闩, 开始等待...");
                    latch.await();
                    SimpleDateFormat sdf = ThreadSafeDateFormatter.get();
                    String time = sdf.format(new Date());
                    System.out.println(Thread.currentThread().getName() + "门闩已打开, 开始执行(放行时间: " + time + ")");
                    result = myCache.compute("13");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + ": " + result);
            });
        }
        Thread.sleep(5000);  // 给5秒钟时间让所有线程做好准备, 然后打开门闩
        latch.countDown();
        service.shutdown();
        while (!service.isTerminated()) {
        }
        long end = System.currentTimeMillis();
        System.out.println("缓存总计耗时: " + ((end - start) * 1.0 / 1000 - 5 - 3) + "秒");
    }
}
