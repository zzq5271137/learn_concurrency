package _01_EightCores._04_Core4_LifeCycle.SynchronizedDemos._03_CorrectCounter;

public class CorrectCounter implements Runnable {
    static CorrectCounter instance = new CorrectCounter();

    static int count = 0;

    @Override
    public void run() {
        synchronized (this) {
            for (int i = 0; i < 100000; i++) {
                count++;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(instance);
        Thread t2 = new Thread(instance);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(count);
    }
}
