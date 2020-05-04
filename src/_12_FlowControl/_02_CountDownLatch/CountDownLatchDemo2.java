package _12_FlowControl._02_CountDownLatch;

/*
 * 演示CountDownLatch的用法二, 即多个线程等待某一个线程的信号, 同时开始执行;
 *
 * 模拟场景:
 * 运动员等待裁判发令, 然后同时起跑;
 */

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CountDownLatchDemo2 {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch begin = new CountDownLatch(1);  // 注意传入的count是1
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
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        System.out.println("裁判员正在检查场地...");
        TimeUnit.SECONDS.sleep(5);
        System.out.println("裁判员发令!");
        begin.countDown();
        service.shutdown();
    }
}
