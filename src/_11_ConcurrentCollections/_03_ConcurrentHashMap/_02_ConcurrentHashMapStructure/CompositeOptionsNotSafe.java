package _11_ConcurrentCollections._03_ConcurrentHashMap._02_ConcurrentHashMapStructure;

/*
 * 对ConcurrentHashMap的组合操作, 并不保证线程安全;
 */

import java.util.concurrent.ConcurrentHashMap;

public class CompositeOptionsNotSafe implements Runnable {
    private static ConcurrentHashMap<String, Integer> scores = new ConcurrentHashMap<>();

    @Override
    public void run() {
        // unsafeIncrement();
        // notRecommendedSafeIncrement();
        recommendedSafeIncrement();
    }

    private void recommendedSafeIncrement() {
        for (int i = 0; i < 1000; i++) {
            /*
             * 使用replace()方法, 就能保证并发安全;
             * 这个方法内部使用了synchronized, 但是只是锁住某一个槽点;
             */
            for (; ; ) {
                Integer score = scores.get("小明");
                if (scores.replace("小明", score, score + 1))
                    break;
            }
        }
    }

    private void notRecommendedSafeIncrement() {
        for (int i = 0; i < 1000; i++) {
            /*
             * 这样确实是线程安全了, 但是并不推荐;
             * 因为我们使用的ConcurrentHashMap, 它里面使用了很大的代价来保证线程安全,
             * 又支持多个线程同时对map进行操作; 但由于我们这里直接使用synchronized,
             * 导致ConcurrentHashMap里面的很多操作都变得无意义了,
             * 同时ConcurrentHashMap依然消耗着很多的资源, 这样一来, 我们还不如直接使用HashMap;
             */
            synchronized (CompositeOptionsNotSafe.class) {
                Integer score = scores.get("小明");
                int newScore = score + 1;
                scores.put("小明", newScore);
            }
        }
    }

    private void unsafeIncrement() {
        for (int i = 0; i < 1000; i++) {
            /*
             * 这里的每个方法都是线程安全的, 但是以这样的方式组合起来用, 就不能保证线程安全;
             */
            Integer score = scores.get("小明");
            int newScore = score + 1;
            scores.put("小明", newScore);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        scores.put("小明", 0);
        Thread t1 = new Thread(new CompositeOptionsNotSafe());
        Thread t2 = new Thread(new CompositeOptionsNotSafe());
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("最终分数: " + scores);
    }
}
