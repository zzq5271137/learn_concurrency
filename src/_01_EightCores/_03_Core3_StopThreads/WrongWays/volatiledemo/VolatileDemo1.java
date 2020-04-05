package _01_EightCores._03_Core3_StopThreads.WrongWays.volatiledemo;

/*
 * 错误的停止线程的方式: 使用volatile设置boolean标记位的方法停止线程;
 * Part 1: 看似可行的部分
 */

public class VolatileDemo1 implements Runnable {
    private volatile boolean cancelled = false;

    @Override
    public void run() {
        int num = 0;
        try {
            while (!cancelled && num <= 100000) {
                if (num % 100 == 0) {
                    System.out.println(num + "是100的倍数");
                }
                num++;
                Thread.sleep(10);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("done");
    }

    public static void main(String[] args) throws InterruptedException {
        VolatileDemo1 r = new VolatileDemo1();
        Thread thread = new Thread(r);
        thread.start();
        Thread.sleep(3000);
        r.cancelled = true; // 想要在这里停止线程
    }
}
