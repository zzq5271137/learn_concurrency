package _01_EightCores._03_Core3_StopThreads;

/*
 * 当run()方法内没有sleep()或wait()等让线程阻塞的方法时, 停止线程的正确方式
 */

public class CorrectWayStopThreadWithoutBlocked {
    public static void main(String[] args) throws InterruptedException {
        Runnable runnable = () -> {
            // 在整数最大值的一半的范围内, 打印10000的倍数
            int num = 0;

            // !Thread.currentThread(): 对其他线程发出的的interrupt()请求做响应
            while (!Thread.currentThread().isInterrupted() && num <= Integer.MAX_VALUE / 2) {
                if (num % 10000 == 0) {
                    System.out.println(num + "是10000的倍数");
                }
                num++;
            }
            System.out.println("任务运行结束");
        };

        Thread thread = new Thread(runnable);
        thread.start();
        Thread.sleep(500);

        // 想要中断线程, 这个被中断的线程需要在run()方法内对interrupt()做响应
        thread.interrupt();
    }
}
