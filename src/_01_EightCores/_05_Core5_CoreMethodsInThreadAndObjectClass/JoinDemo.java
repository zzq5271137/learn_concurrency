package _01_EightCores._05_Core5_CoreMethodsInThreadAndObjectClass;

/*
 * thread.join(): 主线程(执行这条语句的线程)等待thread执行完成, 再往下执行;
 */

public class JoinDemo implements Runnable {
    @Override
    public void run() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "执行完毕");
    }

    public static void main(String[] args) throws InterruptedException {
        Runnable runnable = new JoinDemo();
        Thread t1 = new Thread(runnable);
        Thread t2 = new Thread(runnable);
        t1.start();
        t2.start();
        System.out.println("开始等待子线程运行完毕");
        t1.join();
        t2.join();
        System.out.println("done");
    }
}
