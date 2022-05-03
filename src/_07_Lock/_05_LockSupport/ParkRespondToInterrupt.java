package _07_Lock._05_LockSupport;

/*
 * LockSupport.park()能够响应interrupt请求，会从阻塞状态中恢复继续执行，并且不会抛出InterruptedException
 */

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class ParkRespondToInterrupt {
    public static void main(String[] args) {
        Thread t = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + ": start park");
            LockSupport.park();  // 阻塞当前线程
            System.out.println(Thread.currentThread().getName() + ": unpark from outside, continue work...");
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
            }
            System.out.println(Thread.currentThread().getName() + ": finished");
        });
        t.start();
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Main: interrupt thread");
        t.interrupt();
    }
}
