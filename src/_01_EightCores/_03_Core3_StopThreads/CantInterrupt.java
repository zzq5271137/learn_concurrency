package _01_EightCores._03_Core3_StopThreads;

/*
 * 如果在while里面放try/catch, 会导致响应interrupt()中断失效
 */

public class CantInterrupt {
    public static void main(String[] args) throws InterruptedException {
        Runnable runnable = () -> {
            int num = 0;
            while (!Thread.currentThread().isInterrupted() && num <= 10000) {
                if (num % 100 == 0) {
                    System.out.println(num + "是100的倍数");
                }
                num++;

                /*
                 * 如果在while里面放try/catch, 会导致响应interrupt()中断失效;
                 * 即使我们在while的循环判断里加上!Thread.currentThread().isInterrupted(), 依然会中断失败;
                 * 因为, 这源于sleep()方法的设计理念,
                 * 在sleep过程中如果收到中断请求, 一旦响应了中断(即我们这里的try/catch),
                 * 便会把线程的interrupted的标记位给清除;
                 * 所以, Thread.currentThread().isInterrupted()会返回false;
                 */
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("任务运行结束");
        };

        Thread thread = new Thread(runnable);
        thread.start();
        Thread.sleep(5000);
        thread.interrupt();
    }
}
