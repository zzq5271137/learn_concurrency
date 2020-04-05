package _01_EightCores._03_Core3_StopThreads;

/*
 * 当run()方法内的循环中每次循环都带有sleep()或wait()等让线程阻塞的方法时, 停止线程的正确方式
 */

public class CorrectWayStopThreadWithBlockedInEveryLoop {
    public static void main(String[] args) throws InterruptedException {
        Runnable runnable = () -> {
            int num = 0;
            try {
                while (num <= 10000
                    // && !Thread.currentThread().isInterrupted()
                ) {
                    if (num % 100 == 0) {
                        System.out.println(num + "是100的倍数");
                    }
                    num++;

                    /*
                     * 每次循环都休眠(阻塞);
                     * 这种情况, while循环判断中的!Thread.currentThread().isInterrupted()是多余的;
                     * 因为, 真正响应interrupt()中断的,
                     * 是休眠(阻塞)期间对interrupt()的响应(也就是catch异常和打印异常信息);
                     * 所以, 我们不需要在while循环判断里再去对interrupt()做响应;
                     */
                    Thread.sleep(10);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("任务运行结束");
        };

        Thread thread = new Thread(runnable);
        thread.start();
        Thread.sleep(5000);
        thread.interrupt();
    }
}
