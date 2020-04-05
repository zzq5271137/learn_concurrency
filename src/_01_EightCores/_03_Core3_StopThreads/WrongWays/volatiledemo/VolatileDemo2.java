package _01_EightCores._03_Core3_StopThreads.WrongWays.volatiledemo;

/*
 * 错误的停止线程的方式: 使用volatile设置boolean标记位的方法停止线程;
 * Part 2: 这种方式的局限性是, 当线程正处于长时间阻塞状态, 这种方式是无法停止线程的;
 *
 * 此例演示生产者-消费者模式, 在此例中, 生产者的生产速度很快, 而消费者的消费速度很慢,
 * 所以当阻塞队列满了以后, 生产者会进入阻塞状态, 等待消费者进一步消费;
 * 在此例中, 停止线程失败, 原因是, 使用volatile设置的boolean标记位, 只是在每次循环的循环判断条件中去判断是不是停止,
 * 比如如果刚刚进行了一次循环判断, 此时还没有停止线程的请求(boolean标记位还没有改变), 就会进入循环,
 * 然后由于阻塞队列满了, 生产者就会进入阻塞状态, 如果此时改变了boolean标记位(没用, 因为它只在循环开始前被判断, 而现在处于循环内),
 * 而生产者此时正处于一个长时间的阻塞状态(storage.put(num)), boolean标记位是无法唤醒它的,
 * 由于没人唤醒它, 生产者会一直处于阻塞状态;
 * 而如果使用interrupt()方法去停止生产者线程, 就能够唤醒它, 因为它会抛出一个InterruptedException异常;
 */

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

class Producer implements Runnable {
    public volatile boolean cancelled = false;
    BlockingQueue<Integer> storage;

    public Producer(BlockingQueue<Integer> storage) {
        this.storage = storage;
    }

    @Override
    public void run() {
        int num = 0;
        try {
            while (!cancelled && num <= 100000) {
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

class Consumer {
    BlockingQueue<Integer> storage;

    public Consumer(BlockingQueue<Integer> storage) {
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

public class VolatileDemo2 {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<Integer> storage = new ArrayBlockingQueue<>(10);

        Producer producer = new Producer(storage);
        Thread producerThread = new Thread(producer);
        producerThread.start();
        Thread.sleep(1000);

        Consumer consumer = new Consumer(storage);
        while (consumer.needMoreNums()) {
            consumer.consume();
        }
        System.out.println("消费者不需要更多数据了");
        producer.cancelled = true; // 一旦消费者不需要更多数据了, 我们就应当让生产者也停下来;
    }
}
