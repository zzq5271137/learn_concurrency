package _01_EightCores._03_Core3_StopThreads;

/*
 * 当run()方法内带有sleep()或wait()等让线程阻塞的方法时, 停止线程的正确方式;
 *
 * 拥有响应中断的能力的方法(也就是能让线程进入阻塞状态, 并且在阻塞状态中能够感知到interrupt()请求的方法)总结:
 * 1. Object.wait()/wait(long)/wait(long,int)
 * 2. Thread.sleep(long)/sleep(long,int)
 * 3. Thread.join()/join(long)/join(long,int)
 * 4. java.util.concurrent.BlockingQueue.take()/put(E)
 * 5. java.util.concurrent.locks.Lock.lockInterruptibly()
 * 6. java.util.concurrent.CountDownLatch.await()
 * 7. java.util.concurrent.CyclicBarrier.await()
 * 8. java.util.concurrent.Exchanger.exchange(V)
 * 9. java.nio.channels.InterruptibleChannel相关方法
 * 10. java.nio.channels.Selector相关方法
 */

public class CorrectWayStopThreadWithBlocked {
    public static void main(String[] args) throws InterruptedException {
        Runnable runnable = () -> {
            int num = 0;
            try {
                while (num <= 300
                        && !Thread.currentThread().isInterrupted()
                ) {
                    if (num % 100 == 0) {
                        System.out.println(num + "是100的倍数");
                    }
                    num++;
                }

                /*
                 * 这里, 在休眠(阻塞)期间, 响应interrupt()的方式是, catch异常, 并打印出异常信息;
                 * 也就是, 这个runnable在阻塞期间, 依然可以响应interrupt()中断
                 */
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("任务运行结束");
        };

        Thread thread = new Thread(runnable);
        thread.start();
        Thread.sleep(500);
        thread.interrupt();
    }
}
