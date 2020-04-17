package _08_Atomic._06_Adder;

/*
 * 这里演示多线程情况下AtomicLong的性能, 在高并发场景下对同一个AtomicLong累加;
 * 由于竞争激烈, 每一次加法, 都要flush和refresh(即每次做加法都要将counter进行同步), 导致很耗费资源;
 * (flush即为把线程的工作内存的内容刷回主存, refresh即为把线程的工作内存中的内容置为无效, 重新从主存中读数据)
 */

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

public class AtomicLongDrawbackDemo {
    private static class Task implements Runnable {
        private AtomicLong counter;

        public Task(AtomicLong counter) {
            this.counter = counter;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10000; i++) {
                counter.getAndIncrement();
            }
        }
    }

    public static void main(String[] args) {
        AtomicLong counter = new AtomicLong(0);
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            executorService.submit(new Task(counter));
        }
        executorService.shutdown();
        while (!executorService.isTerminated()) {
        }
        long end = System.currentTimeMillis();
        System.out.println("最终结果: " + counter.get());
        System.out.println("耗时: " + (end - start) + "毫秒");
    }
}
