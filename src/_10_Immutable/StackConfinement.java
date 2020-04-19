package _10_Immutable;

/*
 * 演示栈封闭的两种情况, 即基本类型的变量和引用类型的变量;
 * 先演示线程争抢带来错误的结果, 然后把变量放到方法里去(即栈封闭), 就不会有错误;
 */

public class StackConfinement implements Runnable {
    int sharedCount = 0;

    public void outThread() {
        for (int i = 0; i < 10000; i++) {
            sharedCount++;
        }
    }

    public void inThread() {
        int privateCount = 0;
        for (int i = 0; i < 10000; i++) {
            privateCount++;
        }
        System.out.println(Thread.currentThread().getName() + ", 栈内的变量是私有的, 能够保证线程安全, count = " + privateCount);
    }

    @Override
    public void run() {
        outThread();
        inThread();
    }

    public static void main(String[] args) throws InterruptedException {
        StackConfinement instance = new StackConfinement();
        Thread t1 = new Thread(instance);
        Thread t2 = new Thread(instance);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("栈外的共享变量可能无法保证线程安全, count = " + instance.sharedCount);
    }
}
