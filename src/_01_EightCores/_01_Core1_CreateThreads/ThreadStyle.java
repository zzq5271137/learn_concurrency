package _01_EightCores._01_Core1_CreateThreads;

/*
 * 实现多线程方式二: 继承Thread类的方式实现线程
 */

public class ThreadStyle extends Thread {
    @Override
    public void run() {
        System.out.println("继承Thread类的方式实现线程");
    }

    public static void main(String[] args) {
        ThreadStyle thread = new ThreadStyle();
        thread.start();
    }
}
