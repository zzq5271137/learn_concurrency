package _07_Lock._05_LockSupport;

/*
 * LockSupport定义了一组静态公共方法，这些方法提供了最基本的线程阻塞和唤醒功能。
 * LockSupport是构建同步组件的基础工具，例如AQS中就使用了。
 */

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class LockSupportTest {
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

        System.out.println("Main: before park, [" + t.getState() + "]");
        try {
            TimeUnit.SECONDS.sleep(2);
            System.out.println("Main: thread is parking, [" + t.getState() + "]");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LockSupport.unpark(t);  // 唤醒指定线程线程
        try {
            TimeUnit.MICROSECONDS.sleep(3);
            System.out.println("Main: after unpark, [" + t.getState() + "]");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Main: thread finished, [" + t.getState() + "]");
    }
}
