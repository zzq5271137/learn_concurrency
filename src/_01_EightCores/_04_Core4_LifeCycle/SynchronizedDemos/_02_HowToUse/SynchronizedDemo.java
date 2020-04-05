package _01_EightCores._04_Core4_LifeCycle.SynchronizedDemos._02_HowToUse;

class Demo {
    private final Object LOCK_1 = new Object();
    private final Object LOCK_2 = new Object();

    void demo1() throws InterruptedException {
        synchronized (LOCK_1) {
            System.out.println(Thread.currentThread().getName() + "进入demo1");
            Thread.sleep(3000);
            System.out.println(Thread.currentThread().getName() + "离开demo1");
        }
    }

    void demo2() throws InterruptedException {
        synchronized (LOCK_2) {
            System.out.println(Thread.currentThread().getName() + "进入demo2");
            Thread.sleep(3000);
            System.out.println(Thread.currentThread().getName() + "离开demo2");
        }
    }

}

class MyThread1 implements Runnable {
    private Demo demo;

    public MyThread1(Demo demo) {
        this.demo = demo;
    }

    @Override
    public void run() {
        try {
            demo.demo1();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class MyThread2 implements Runnable {
    private Demo demo;

    public MyThread2(Demo demo) {
        this.demo = demo;
    }

    @Override
    public void run() {
        try {
            demo.demo2();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class SynchronizedDemo {
    public static void main(String[] args) throws InterruptedException {
        Demo demo = new Demo();
        Thread thread1 = new Thread(new MyThread1(demo));
        Thread thread2 = new Thread(new MyThread2(demo));
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println("done");
    }
}
