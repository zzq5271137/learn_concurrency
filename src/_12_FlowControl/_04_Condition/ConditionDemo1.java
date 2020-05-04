package _12_FlowControl._04_Condition;

/*
 * 演示Condition的基本用法;
 */

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionDemo1 {
    private ReentrantLock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void method1() throws InterruptedException {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "开始await(), 等待条件满足...");
            condition.await();
            System.out.println(Thread.currentThread().getName() + "条件已满足, 开始执行任务");
        } finally {
            lock.unlock();
        }
    }

    public void method2() throws InterruptedException {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "开始执行准备工作...");
            TimeUnit.SECONDS.sleep(3);
            System.out.println(Thread.currentThread().getName() + "准备工作完成, 条件已满足, 唤醒其他线程");
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ConditionDemo1 instance = new ConditionDemo1();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    instance.method1();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        TimeUnit.MILLISECONDS.sleep(200);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    instance.method2();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
