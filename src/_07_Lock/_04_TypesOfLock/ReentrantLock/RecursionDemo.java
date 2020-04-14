package _07_Lock._04_TypesOfLock.ReentrantLock;

/*
 * 利用可重入性对资源进行递归处理;
 */

import java.util.concurrent.locks.ReentrantLock;

public class RecursionDemo {
    private static ReentrantLock lock = new ReentrantLock();

    private static void accessResource() {
        lock.lock();
        try {
            int count = lock.getHoldCount();
            System.out.println("对资源进行了" + count + "次处理");
            if (count < 5) {
                accessResource();
            }
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        accessResource();
    }
}
