package _01_EightCores._03_Core3_StopThreads.WrongWays.volatiledemo;

/*
 * 错误的停止线程的方式: 使用volatile设置boolean标记位的方法停止线程;
 * Part 3: 使用interrupt()方法去解决VolatileDemo2.java中的问题;
 */

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

class Producer2 implements Runnable {
    BlockingQueue<Integer> storage;

    public Producer2(BlockingQueue<Integer> storage) {
        this.storage = storage;
    }

    @Override
    public void run() {
        int num = 0;
        try {
            /*
             * 这里的!Thread.currentThread().isInterrupted()的判断是需要的;
             * 如果阻塞队列满了, 它会进入阻塞状态, 就可以响应interrupt()中断(抛出异常);
             * 但如果阻塞队列没有满时想要停止此线程, 就需要在while的循环判断中去判断(跳出循环);
             */
            while (!Thread.currentThread().isInterrupted() && num <= 100000) {
                if (num % 100 == 0) {
                    System.out.println(num + "是100的倍数, 被放到仓库中");
                    storage.put(num);
                }
                num++;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println("生产者停止运行");
        }
    }
}

class Consumer2 {
    BlockingQueue<Integer> storage;

    public Consumer2(BlockingQueue<Integer> storage) {
        this.storage = storage;
    }

    public boolean needMoreNums() {
        return !(Math.random() > 0.95);
    }

    public void consume() throws InterruptedException {
        System.out.println(storage.take() + "被消费了");
        Thread.sleep(100);
    }
}

public class VolatileDemo3 {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<Integer> storage = new ArrayBlockingQueue<>(10);

        Producer2 producer = new Producer2(storage);
        Thread producerThread = new Thread(producer);
        producerThread.start();
        Thread.sleep(1000);

        Consumer2 consumer = new Consumer2(storage);
        while (consumer.needMoreNums()) {
            consumer.consume();
        }
        System.out.println("消费者不需要更多数据了");
        producerThread.interrupt(); // 一旦消费者不需要更多数据了, 我们就应当让生产者也停下来;
    }
}
