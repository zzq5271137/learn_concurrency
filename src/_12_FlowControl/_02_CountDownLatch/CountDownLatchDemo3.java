package _12_FlowControl._02_CountDownLatch;

/*
 * 演示CountDownLatch的用法三, 即既有一等多, 也有多等一的场景;
 *
 * 模拟场景:
 * 还是运动员等待裁判发令, 然后同时起跑的场景, 不过这里加了一个, 在终点的裁判员等待所有运动员都通过终点后,
 * 宣布本场比赛结束;
 */

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CountDownLatchDemo3 {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch begin = new CountDownLatch(1);
        CountDownLatch end = new CountDownLatch(5);
        ExecutorService service = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            final int no = i + 1;
            service.submit(new Runnable() {
                @Override
                public void run() {
                    System.out.println(no + "号运动员准备完毕, 等待发令枪...");
                    try {
                        begin.await();
                        System.out.println(no + "号运动员起跑!");
                        Thread.sleep((long) (Math.random() * 10000));
                        System.out.println(no + "号运动员通过终点!");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        end.countDown();
                    }
                }
            });
        }
        System.out.println("裁判员正在检查场地...");
        TimeUnit.SECONDS.sleep(5);
        System.out.println("裁判员发令!");
        begin.countDown();
        end.await();
        System.out.println("所有人通过终点, 本场比赛结束!");
        service.shutdown();
    }
}
