package _12_FlowControl._02_CountDownLatch;

/*
 * 演示CountDownLatch的用法一, 即一个线程等待多个线程都执行完毕, 再继续自己的工作;
 *
 * 模拟场景:
 * 工厂中的质检环节, 质检流程上有5个质检员, 只有当所有质检员都通过之后, 这个产品才能进入下一个环节;
 */

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CountDownLatchDemo1 {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(5);
        ExecutorService service = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            final int no = i + 1;
            service.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep((long) (Math.random() * 10000));
                        System.out.println("No." + no + "完成质检");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        latch.countDown();
                    }
                }
            });
        }
        System.out.println("等待5个质检员检查结束...");
        latch.await();
        System.out.println("完成所有质检");
        service.shutdown();
    }
}
