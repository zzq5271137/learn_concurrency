package _01_EightCores._04_Core4_LifeCycle.SynchronizedDemos._04_OtherCases;

/*
 * 当一个synchronized同步方法抛出异常后, 会释放锁;
 * 一个synchronized同步方法无论是正常执行完毕还是中途抛出异常, 都会释放锁;
 */

public class SynchronizedException implements Runnable {
    static SynchronizedException instance = new SynchronizedException();

    public synchronized void func() throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + "进入func");
        Thread.sleep(3000);
        int num = 10 / 0;
        System.out.println(Thread.currentThread().getName() + "离开func");
    }

    @Override
    public void run() {
        try {
            func();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(instance);
        Thread t2 = new Thread(instance);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("done");
    }
}
