package _12_FlowControl._05_CyclicBarrier;

/*
 * 演示CyclicBarrier的基本用法;
 */

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierDemo {
    static class Task implements Runnable {
        private int id;
        private CyclicBarrier cyclicBarrier;

        public Task(int id, CyclicBarrier cyclicBarrier) {
            this.id = id;
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            try {
                System.out.println("线程" + id + "开始执行任务...");
                Thread.sleep((long) (Math.random() * 7000));
                System.out.println("线程" + id + "执行完部分任务, 到达集合点, 开始等待其他线程到达");
                cyclicBarrier.await();
                System.out.println("线程" + id + "从集合点出发, 开始执行剩下的任务...");
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(5, new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("人满了, 大家统一出发, 倒计时3秒...");
                    Thread.sleep(500);
                    for (int i = 3; i > 0; i--) {
                        System.out.println(i + "...");
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        for (int i = 0; i < 5; i++) {
            new Thread(new Task(i + 1, cyclicBarrier)).start();
        }
    }
}
