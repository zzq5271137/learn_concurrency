package _07_Lock._02_LockDemos;

/*
 * 演示用tryLock()来避免死锁;
 *
 * 在Lock的源码中, 有对获取锁以及释放锁的书写规范:
 *     if (lock.tryLock()) {
 *         try {
 *             // manipulate protected state
 *         } finally {
 *             lock.unlock();
 *         }
 *     } else {
 *         // perform alternative actions
 *     }
 * 我们在使用Lock时, 最好要遵循这种写法;
 */

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TryLockDemo implements Runnable {
    static Lock lock1 = new ReentrantLock();
    static Lock lock2 = new ReentrantLock();
    int flag;

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            if (flag == 0) {
                try {
                    if (lock1.tryLock(800, TimeUnit.MILLISECONDS)) {
                        try {
                            System.out.println(Thread.currentThread().getName() + "已获取lock1, 尝试获取lock2");
                            Thread.sleep(new Random().nextInt(1000));
                            if (lock2.tryLock(800, TimeUnit.MILLISECONDS)) {
                                try {
                                    System.out.println(Thread.currentThread().getName() + "已获取lock2, 两把锁均已获取, 开始运行逻辑...");
                                    break;
                                } finally {
                                    lock2.unlock();
                                    System.out.println(Thread.currentThread().getName() + "释放lock2");
                                }
                            } else {
                                System.out.println(Thread.currentThread().getName() + "获取lock2失败, 释放lock1, 并重试...");
                                Thread.sleep(new Random().nextInt(1000));
                            }
                        } finally {
                            lock1.unlock();
                            Thread.sleep(new Random().nextInt(1000));
                            System.out.println(Thread.currentThread().getName() + "释放lock1");
                        }
                    } else {
                        System.out.println(Thread.currentThread().getName() + "获取lock1失败, 正在重试...");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (flag == 1) {
                try {
                    if (lock2.tryLock(800, TimeUnit.MILLISECONDS)) {
                        try {
                            System.out.println(Thread.currentThread().getName() + "已获取lock2, 尝试获取lock1");
                            Thread.sleep(new Random().nextInt(1000));
                            if (lock1.tryLock(800, TimeUnit.MILLISECONDS)) {
                                try {
                                    System.out.println(Thread.currentThread().getName() + "已获取lock1, 两把锁均已获取, 开始运行逻辑...");
                                    break;
                                } finally {
                                    lock1.unlock();
                                    System.out.println(Thread.currentThread().getName() + "释放lock1");
                                }
                            } else {
                                System.out.println(Thread.currentThread().getName() + "获取lock1失败, 释放lock2, 并重试...");
                                Thread.sleep(new Random().nextInt(1000));
                            }
                        } finally {
                            lock2.unlock();
                            Thread.sleep(new Random().nextInt(1000));
                            System.out.println(Thread.currentThread().getName() + "释放lock2");
                        }
                    } else {
                        System.out.println(Thread.currentThread().getName() + "获取lock2失败, 正在重试...");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        TryLockDemo r1 = new TryLockDemo();
        TryLockDemo r2 = new TryLockDemo();
        r1.flag = 0;
        r2.flag = 1;
        new Thread(r1).start();
        new Thread(r2).start();
    }
}
