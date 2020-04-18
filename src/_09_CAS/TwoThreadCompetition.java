package _09_CAS;

/*
 * 模拟两个线程同时进行CAS操作, 会有一个失败;
 */

public class TwoThreadCompetition implements Runnable {
    private volatile int value;

    /*
     * 这里使用锁去保证原子性, 是为了让Java代码模拟CAS操作;
     * 但真正的CAS操作(比如atomic包中各种类使用的CAS), 是由CPU保证其原子性的(CPU的特殊指令);
     * 这里的整个方法, 实际上模拟的是一条CPU指令, 它在CPU层面运行是具有原子性的;
     */
    public synchronized int compareAndSwap(int expectedValue, int newValue) {
        int oldValue = value;
        if (oldValue == expectedValue) {
            value = newValue;
        }
        return oldValue;
    }

    @Override
    public void run() {
        compareAndSwap(0, 1);
    }

    public static void main(String[] args) throws InterruptedException {
        TwoThreadCompetition instance = new TwoThreadCompetition();
        instance.value = 0;
        Thread t1 = new Thread(instance, "Thread-1");
        Thread t2 = new Thread(instance, "Thread-2");
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(instance.value);
    }
}
