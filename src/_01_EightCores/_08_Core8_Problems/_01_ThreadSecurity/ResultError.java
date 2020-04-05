package _01_EightCores._08_Core8_Problems._01_ThreadSecurity;

/*
 * 线程安全问题之运行结果错误;
 *
 * 这里演示count++运行结果不准确, 并找出具体出错的位置;
 */

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;

public class ResultError implements Runnable {
    int count = 0;
    boolean[] marked = new boolean[200001];
    AtomicInteger realCount = new AtomicInteger();
    AtomicInteger wrongCount = new AtomicInteger();
    volatile CyclicBarrier cyclicBarrier1 = new CyclicBarrier(2);
    volatile CyclicBarrier cyclicBarrier2 = new CyclicBarrier(2);

    @Override
    public void run() {
        marked[0] = true;
        for (int i = 0; i < 100000; i++) {
            try {
                cyclicBarrier2.reset();
                cyclicBarrier1.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
            count++;
            try {
                cyclicBarrier1.reset();
                cyclicBarrier2.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
            realCount.incrementAndGet();
            synchronized (this) {
                if (marked[count] && marked[count - 1]) {
                    System.out.println("发生错误@" + count);
                    wrongCount.incrementAndGet();
                }
                marked[count] = true;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ResultError instance = new ResultError();
        Thread t1 = new Thread(instance);
        Thread t2 = new Thread(instance);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("表面上的结果是: " + instance.count);
        System.out.println("真正执行count++的次数: " + instance.realCount);
        System.out.println("错误次数: " + instance.wrongCount);
    }
}
