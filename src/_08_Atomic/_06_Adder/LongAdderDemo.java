package _08_Atomic._06_Adder;

/*
 * 这里演示多线程情况下LongAdder的性能, 与AtomicLongDrawbackDemo.java执行同样的任务;
 * 通过运行结果的对比可以看到, LongAdder的执行效率比AtomicLong要高出很多;
 * 原因在于, 对于AtomicLong来说, 每一次加法, 都要flush和refresh, 这是很耗费资源的;
 * 但是对于LongAdder来说, 它为每个线程准备了独自的counter, 每个线程各自累加各自的counter,
 * 累加过程中并没有进行counter的同步工作(没有flush和refresh); 也就是说, 使用LongAdder,
 * 实际上是每个线程在自己独立的工作内存中去累加的, 并不是去累加主内存中公共的那个counter;
 * 更准确和具体地说, LongAdder引入了分段累加的概念, 内部有一个base变量和一个Cell[]数组共同参与计数;
 * 上面提到过, 使用LongAdder实际上是每个线程在自己独立的工作内存中去累加, 其实不完全是这样; 实际情况是:
 * 1). 当竞争不激烈的情况下, 会直接累加到base变量上(使用CAS对base变量进行累加);
 * 2). 当竞争激烈时, 各个线程分散累加到自己的Cell[i]槽中(使用Hash值给不同线程分配不同Cell[i]),
 *     每一个Cell[i]都是一个独立的计数器; 所以LongAdder本质上是空间换时间;
 * 在调用sum()的时候, 会把base和Cell数组中的所有值进行求和并返回, sum()源码:
 *     public long sum() {
 *         Cell[] as = cells; Cell a;
 *         long sum = base;
 *         if (as != null) {
 *             for (int i = 0; i < as.length; ++i) {
 *                if ((a = as[i]) != null)
 *                    sum += a.value;
 *             }
 *         }
 *         return sum;
 *     }
 * 但是注意一点, 这里的sum()方法并没使用锁(并没有同步); 所以我们如果在累加的过程中调用sum(),
 * 得到的值可能就是不准确的, 因为它无法包含之后的累加;
 */

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.LongAdder;

public class LongAdderDemo {
    private static class Task implements Runnable {
        private LongAdder counter;

        public Task(LongAdder counter) {
            this.counter = counter;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10000; i++) {
                counter.increment();
            }
        }
    }

    public static void main(String[] args) {
        LongAdder counter = new LongAdder();
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            executorService.submit(new Task(counter));
        }
        executorService.shutdown();
        while (!executorService.isTerminated()) {
        }
        long end = System.currentTimeMillis();
        System.out.println("最终结果: " + counter.sum());
        System.out.println("耗时: " + (end - start) + "毫秒");
    }
}
