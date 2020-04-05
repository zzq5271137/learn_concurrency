package _01_EightCores._04_Core4_LifeCycle.SynchronizedDemos._01_Introduction;

/*
 * synchronized关键字的作用:
 * Synchronized methods enable a simple strategy for preventing thread interference and memory
 * consistency errors: if an object is visible to more than one thread, all reads or write to
 * that object's variables are done through synchronized methods;
 * 简而言之, synchronized的作用就是能够保证在同一时刻最多只有一个线程去执行该段代码, 以达到保证并发安全的效果;
 * synchronized关键字是一种并发手段;
 *
 * 如果不使用并发手段, 会得到不可预见的结果;
 */

public class DisappearedRequest implements Runnable {
    static DisappearedRequest instance = new DisappearedRequest();

    static int count = 0;

    @Override
    public void run() {
        for (int i = 0; i < 100000; i++) {
            /*
             * 看上去只有一个操作, 实际上有三个操作:
             * 1. 读取count
             * 2. 将count加一
             * 3. 将count的值写入到内存中
             */
            count++;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(instance);
        Thread t2 = new Thread(instance);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(count);
    }
}
