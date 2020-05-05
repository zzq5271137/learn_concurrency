package _13_AQS._03_DIYLatchUsingAQS;

/*
 * 使用AQS实现一个简单的线程协作器, 一次性门闩, 即倒数一次, 就打开门闩;
 * 我们约定, 当创建OneShotLatch实例时, state默认等于0;
 * 如果state等于0, 表示门闩关闭, 这时如果有线程调用await()方法, 则需要阻塞;
 * 如果state等于1, 表示门闩打开(即调用signal()方法会将state置为1);
 */

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class OneShotLatch {

    private static class Sync extends AbstractQueuedSynchronizer {
        /**
         * 重写的获取锁的方法, 由于我们的逻辑是一个共享锁的逻辑, 所以重写tryAcquireShared()方法
         */
        @Override
        protected int tryAcquireShared(int arg) {
            return getState() == 1 ? 1 : -1;
        }

        /**
         * 重写的释放锁的方法, 由于我们的逻辑是一个共享锁的逻辑, 所以重写tryReleaseShared()方法
         */
        @Override
        protected boolean tryReleaseShared(int arg) {
            setState(1);
            return true;
        }
    }

    private final Sync sync = new Sync();

    /**
     * 获取的方法, 这是固定的模板
     */
    public void await() {
        sync.acquireShared(0);
    }

    /**
     * 释放的方法, 这是固定的模板
     */
    public void signal() {
        sync.releaseShared(0);
    }

    /**
     * 测试我们的一次性门闩
     */
    public static void main(String[] args) throws InterruptedException {
        OneShotLatch latch = new OneShotLatch();
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + "抵达门闩, 等待门闩打开...");
                    latch.await();
                    System.out.println(Thread.currentThread().getName() + "门闩已打开, 继续执行");
                }
            }).start();
        }
        Thread.sleep(1000);
        System.out.println("Main: 5秒后打开门闩, 倒计时");
        Thread.sleep(500);
        for (int i = 5; i > 0; i--) {
            System.out.println(i + "...");
            Thread.sleep(1000);
        }
        latch.signal();
    }
}
