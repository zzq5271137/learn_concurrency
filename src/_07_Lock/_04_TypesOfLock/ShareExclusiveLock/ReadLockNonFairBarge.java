package _07_Lock._04_TypesOfLock.ShareExclusiveLock;

/*
 * 演示非公平的读写锁中读锁插队的情景(等待队列头结点的线程不是请求写锁的线程时, 读锁可以插队);
 * 其实这个例子既演示了读锁可以插队的情况, 又演示了读锁不能插队的情况,
 * 注意观察ReadThread_1和ReadThread_2打印出"成功获取读锁, 正在读取"的周围, "子线程创建的Thread"的打印情况;
 * 在打印出"WriteThread_1释放写锁"和"ReadThread_2成功获取读锁, 正在读取"之间, 会有"子线程创建的Thread"进行读锁的插队;
 * 一旦ReadThread_2获取到读锁之后, 就不允许"子线程创建的Thread"进行读锁插队了, 因为此时,
 * 在等待队列队首的是一个等待写锁的线程(WriteThread_2);
 */

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadLockNonFairBarge {
    private static ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock(false);
    private static ReentrantReadWriteLock.ReadLock readLock = reentrantReadWriteLock.readLock();
    private static ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock.writeLock();

    private static void read() {
        System.out.println(Thread.currentThread().getName() + "开始尝试获取读锁");
        readLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "成功获取读锁, 正在读取");
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName() + "释放读锁");
            readLock.unlock();
        }
    }

    private static void write() {
        System.out.println(Thread.currentThread().getName() + "开始尝试获取写锁");
        writeLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "成功获取写锁, 正在写入");
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName() + "释放写锁");
            writeLock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new Thread(ReadLockNonFairBarge::write, "WriteThread_1").start();
        Thread.sleep(15);
        new Thread(ReadLockNonFairBarge::read, "ReadThread_1").start();
        Thread.sleep(15);
        new Thread(ReadLockNonFairBarge::read, "ReadThread_2").start();
        Thread.sleep(15);
        new Thread(ReadLockNonFairBarge::write, "WriteThread_2").start();
        Thread.sleep(15);
        new Thread(ReadLockNonFairBarge::read, "ReadThread_3").start();
        Thread.sleep(20);

        new Thread(new Runnable() {
            @Override
            public void run() {
                Thread[] threads = new Thread[1000];
                for (int i = 0; i < 1000; i++) {
                    threads[i] = new Thread(ReadLockNonFairBarge::read, "子线程创建的Thread-" + i);
                }
                for (int i = 0; i < 1000; i++) {
                    threads[i].start();
                }
            }
        }).start();
    }
}
