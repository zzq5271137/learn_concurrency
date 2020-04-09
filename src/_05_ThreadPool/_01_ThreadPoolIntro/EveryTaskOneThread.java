package _05_ThreadPool._01_ThreadPoolIntro;

/*
 * 演示每一个任务都创建一个线程(非常消耗资源, 效率低);
 */

class Task implements Runnable {
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "执行了任务");
    }
}

public class EveryTaskOneThread {
    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            new Thread(new Task()).start();
        }
    }
}
