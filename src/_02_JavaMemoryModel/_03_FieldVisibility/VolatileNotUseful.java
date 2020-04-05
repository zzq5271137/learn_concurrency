package _02_JavaMemoryModel._03_FieldVisibility;

/*
 * volatile并不适用于a++的情况, 因为volatile只能保证单个的对volatile变量的读/写具有原子性,
 * 而a++属于复合(多个)的对volatile变量的读/写, 所以这种计算并不具备原子性;
 * 详见VolatilePrinciple.java
 */

import java.util.concurrent.atomic.AtomicInteger;

public class VolatileNotUseful implements Runnable {
    volatile int a;
    AtomicInteger realA = new AtomicInteger();

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            a++;
            realA.incrementAndGet();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        VolatileNotUseful instance = new VolatileNotUseful();
        Thread t1 = new Thread(instance);
        Thread t2 = new Thread(instance);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("最终结果, a = " + instance.a);
        System.out.println("实际结果, realA = " + instance.realA.get());
    }
}
