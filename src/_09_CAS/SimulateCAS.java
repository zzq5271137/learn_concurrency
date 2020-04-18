package _09_CAS;

/*
 * 模拟CAS操作, 等价代码;
 */

public class SimulateCAS {
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

    public static void main(String[] args) {
    }
}
