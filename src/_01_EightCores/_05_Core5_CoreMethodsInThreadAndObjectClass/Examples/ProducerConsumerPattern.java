package _01_EightCores._05_Core5_CoreMethodsInThreadAndObjectClass.Examples;

/*
 * 使用wait()/notify(), 手写生产者消费者模式
 */

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

class EventStorage {
    private int maxSize;
    private LinkedList<Date> storage;
    private SimpleDateFormat sdf;

    public EventStorage() {
        maxSize = 10;
        storage = new LinkedList<>();
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    public synchronized void put() throws InterruptedException {
        while (storage.size() == maxSize) {
            wait();
        }
        storage.add(new Date());
        System.out.println("生产一个产品, 现在仓库里有" + storage.size() + "个产品");
        notify();
    }

    public synchronized void take() throws InterruptedException {
        while (storage.size() == 0) {
            wait();
        }
        System.out.println("消费" + sdf.format(storage.poll()) + ", 现在仓库还剩下" + storage.size() + "个产品");
        notify();
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
            for (int i = 0; i < 100; i++) {
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
            for (int i = 0; i < 100; i++) {
                storage.take();
                Thread.sleep(3000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class ProducerConsumerPattern {
    public static void main(String[] args) {
        EventStorage storage = new EventStorage();
        new Thread(new Producer(storage)).start();
        new Thread(new Consumer(storage)).start();
    }
}
