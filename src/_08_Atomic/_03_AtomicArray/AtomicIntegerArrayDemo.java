package _08_Atomic._03_AtomicArray;

/*
 * 演示AtomicIntegerArray的基本用法;
 */

import java.util.concurrent.atomic.AtomicIntegerArray;

class Decrementer implements Runnable {
    private AtomicIntegerArray array;

    public Decrementer(AtomicIntegerArray array) {
        this.array = array;
    }

    @Override
    public void run() {
        for (int i = 0; i < array.length(); i++) {
            // 这里的参数指的是数组的下标, 即对数组中哪一个元素进行操作
            array.getAndDecrement(i);
        }
    }
}

class Incrementer implements Runnable {
    private AtomicIntegerArray array;

    public Incrementer(AtomicIntegerArray array) {
        this.array = array;
    }

    @Override
    public void run() {
        for (int i = 0; i < array.length(); i++) {
            array.getAndIncrement(i);
            array.getAndIncrement(i);
            array.getAndIncrement(i);
        }
    }
}

public class AtomicIntegerArrayDemo {
    public static void main(String[] args) throws InterruptedException {
        AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(1000);
        Incrementer incrementer = new Incrementer(atomicIntegerArray);
        Decrementer decrementer = new Decrementer(atomicIntegerArray);
        Thread[] incrementerThreads = new Thread[100];
        Thread[] decrementerThreads = new Thread[100];
        for (int i = 0; i < 100; i++) {
            incrementerThreads[i] = new Thread(incrementer);
            decrementerThreads[i] = new Thread(decrementer);
        }
        for (int i = 0; i < 100; i++) {
            incrementerThreads[i].start();
            incrementerThreads[i].join();
            decrementerThreads[i].start();
            decrementerThreads[i].join();
        }
        boolean foundError = false;
        for (int i = 0; i < atomicIntegerArray.length(); i++) {
            if (atomicIntegerArray.get(i) != 200) {
                System.out.println("发现了不为200的值, 序号: " + i + ", 值: " + atomicIntegerArray.get(i));
                foundError = true;
            }
        }
        if (!foundError) {
            System.out.println("所有元素的值都为200");
        }
    }
}
