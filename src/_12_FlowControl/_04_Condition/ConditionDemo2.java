package _12_FlowControl._04_Condition;

/*
 * 演示使用Condition实现生产者消费者模式;
 */

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

class EventStorage {
    private int maxQueueSize;
    Queue<Integer> queue;
    ReentrantLock lock = new ReentrantLock();
    Condition notFull = lock.newCondition();
    Condition notEmpty = lock.newCondition();

    public EventStorage() {
        maxQueueSize = 10;
        queue = new LinkedList<>();
    }

    public void put() throws InterruptedException {
        lock.lock();
        try {
            while (queue.size() == maxQueueSize) {
                System.out.println("Producer: 队列满, 等待位置");
                notFull.await();
            }
            int num = new Random().nextInt(90) + 10;
            queue.offer(num);
            System.out.println("Producer: 生产-" + num + ", 队列含有" + queue.size() + "个产品");
            notEmpty.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public void take() throws InterruptedException {
        lock.lock();
        try {
            while (queue.size() == 0) {
                System.out.println("Consumer: 队列空, 等待数据");
                notEmpty.await();
            }
            System.out.println("Consumer: 消费-" + queue.poll() + ", 队列剩余" + queue.size() + "个产品");
            notFull.signalAll();
        } finally {
            lock.unlock();
        }
    }
}

class Producer implements Runnable {
    private EventStorage storage;

    public Producer(EventStorage storage) {
        this.storage = storage;
    }

    @Override
    public void run() {
        try {
            while (true) {
                storage.put();
                Thread.sleep(300);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Consumer implements Runnable {
    private EventStorage storage;

    public Consumer(EventStorage storage) {
        this.storage = storage;
    }

    @Override
    public void run() {
        try {
            while (true) {
                storage.take();
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class ConditionDemo2 {
    public static void main(String[] args) {
        EventStorage storage = new EventStorage();
        new Thread(new Producer(storage)).start();
        new Thread(new Consumer(storage)).start();
    }
}
