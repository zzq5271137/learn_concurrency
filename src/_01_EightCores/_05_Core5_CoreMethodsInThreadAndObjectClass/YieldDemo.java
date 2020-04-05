package _01_EightCores._05_Core5_CoreMethodsInThreadAndObjectClass;

/*
 * 执行yield()方法的线程, 依然处于RUNNABLE状态, 而不是WAITING;
 * 执行yield()方法的线程, 并不会释放掉自己的锁, 也不会陷入阻塞状态, 下一次CPU调度依然可能随时调度它;
 * yield()方法的作用是, 让出自己当前的CPU时间片, 从而让其他具有相同优先级的等待线程获取执行权;
 * 但是, 并不能保证在当前线程调用yield()之后, 其他具有相同优先级的线程就一定能够获得执行权,
 * 也有可能是这个执行yield()的线程再一次获得CPU时间片而继续执行;
 * 线程是有优先级的, 但并不一定代表, 优先级越高的线程就一定优先获得CPU时间片,
 * 优先级高的线程仅仅是获得CPU时间片的概率大了一些而已, 最终获得CPU时间片的可能是优先级最低的线程;
 * 所谓的优先执行, 是在大量执行次数中才能体现出来的;
 *
 * 根据以上描述, 下面程序执行的结果可以说是随机的;
 */

class MyRunnable implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println(Thread.currentThread().getName() + "--" + i);
            Thread.yield();
        }
    }
}

public class YieldDemo {
    public static void main(String[] args) {
        Thread highPriorityThread = new Thread(new MyRunnable(), "HighPriorityThread");
        Thread lowPriorityThread = new Thread(new MyRunnable(), "lowPriorityThread");
        highPriorityThread.setPriority(Thread.MAX_PRIORITY);
        lowPriorityThread.setPriority(Thread.MIN_PRIORITY);
        highPriorityThread.start();
        lowPriorityThread.start();
    }
}
