package _11_ConcurrentCollections._05_ConcurrentQueue._02_BlockingQueue;

/*
 * ArrayBlockingQueue:
 * 1. 底层使用数组去存放数据;
 * 2. 是有界队列, 创建它时需要指定容量, 不扩容;
 * 3. 可以指定是否需要保证公平, 如果是公平的话, 那么等待了最长时间的线程会被优先处理,
 *    不过这同时会带来一定的性能损耗;
 * 4. 使用一把锁(ReentrantLock);
 *
 * 模拟场景:
 * 有10个面试者, 一共只有1个面试官, 大厅里有3个位子供面试者休息, 每个人的面试时间是10秒(生产者消费者模式);
 */

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/*
 * 生产者
 */
class IntervieweTask implements Runnable {
    BlockingQueue<String> queue;

    public IntervieweTask(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        System.out.println("开始安排候选人");
        for (int i = 0; i < 10; i++) {
            String candidate = "Candidate_" + (i + 1);
            try {
                queue.put(candidate);
                System.out.println("已安排" + candidate);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            queue.put("stop");
            System.out.println("已安排完所有候选人");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

/*
 * 消费者
 */
class Boss implements Runnable {
    BlockingQueue<String> queue;

    public Boss(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            System.out.println("开始面试候选人");
            String msg;
            while (!(msg = queue.take()).equals("stop")) {
                System.out.println("开始对" + msg + "进行面试");
                TimeUnit.SECONDS.sleep(2);
            }
            System.out.println("已面试完所有候选人");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class ArrayBlockingQueueDemo {
    public static void main(String[] args) {
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(3, true);
        new Thread(new IntervieweTask(queue)).start();
        new Thread(new Boss(queue)).start();
    }
}
