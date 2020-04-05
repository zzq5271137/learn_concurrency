package _01_EightCores._05_Core5_CoreMethodsInThreadAndObjectClass;

/*
 * 展示join()的等价代码
 */

public class JoinPrinciple implements Runnable {
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
        Thread thread = new Thread(new JoinPrinciple());
        thread.start();
        System.out.println("开始等待子线程运行完毕");

        // thread.join()的等价代码(其实就是利用了Thread类的实例对象在执行完run()方法后会自动调用this.notifyAll())
        synchronized (thread) {
            thread.wait();
        }

        System.out.println("done");
    }
}
