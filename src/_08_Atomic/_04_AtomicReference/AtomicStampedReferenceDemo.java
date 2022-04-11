package _08_Atomic._04_AtomicReference;

/*
 * 演示使用AtomicStampedReference解决ABA问题
 */

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

public class AtomicStampedReferenceDemo {
    private static AtomicInteger index = new AtomicInteger(10);

    /**
     * 演示CAS的ABA问题
     */
    private static void aba() throws InterruptedException {
        new Thread(() -> {
            int expectedValue = index.get();
            System.out.println(Thread.currentThread().getName() + " 期待值：" + expectedValue);
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            boolean isSuccess = index.compareAndSet(10, 12);
            System.out.println(Thread.currentThread().getName() + "：index是否是预期的10，" + isSuccess + "，设置的新值是：" + index.get());
        }, "李四").start();
        TimeUnit.MILLISECONDS.sleep(100);
        new Thread(() -> {
            index.compareAndSet(10, 11);
            index.compareAndSet(11, 10);  // ABA
            System.out.println(Thread.currentThread().getName() + "：10 -> 11 -> 10");
        }, "张三").start();
    }

    private static AtomicStampedReference<Integer> stampRef = new AtomicStampedReference<>(10, 1);

    private static void solveABA() throws InterruptedException {
        new Thread(() -> {
            int expectedValue = stampRef.getReference();
            int expectedStamp = stampRef.getStamp();
            System.out.println(Thread.currentThread().getName() + " 期待值：" + expectedValue + "，期待版本号：" + expectedStamp);
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            boolean isSuccess = stampRef.compareAndSet(10, 12, expectedStamp, expectedStamp + 1);
            System.out.println(Thread.currentThread().getName() + " 修改是否成功：" + isSuccess + "，当前值：" + stampRef.getReference() + "，当前版本号：" + stampRef.getStamp());
        }, "李四").start();
        TimeUnit.MILLISECONDS.sleep(100);
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " 第1次版本号：" + stampRef.getStamp());
            stampRef.compareAndSet(10, 11, stampRef.getStamp(), stampRef.getStamp() + 1);
            System.out.println(Thread.currentThread().getName() + " 第2次版本号：" + stampRef.getStamp());
            stampRef.compareAndSet(11, 10, stampRef.getStamp(), stampRef.getStamp() + 1);
            System.out.println(Thread.currentThread().getName() + " 第3次版本号：" + stampRef.getStamp());
        }, "张三").start();
    }

    public static void main(String[] args) throws InterruptedException {
//        aba();
        solveABA();
    }
}
